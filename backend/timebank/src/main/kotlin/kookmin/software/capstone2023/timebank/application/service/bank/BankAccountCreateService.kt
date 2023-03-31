package kookmin.software.capstone2023.timebank.application.service.bank
import kookmin.software.capstone2023.timebank.application.exception.ConflictException
import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.domain.model.*
import kookmin.software.capstone2023.timebank.domain.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BankAccountCreateService (
    private val accountRepository: AccountJpaRepository,
    private val bankAccountRepository: BankAccountJpaRepository,
    private val bankBranchRepository: BankBranchRepository,
    private val userRepository: UserJpaRepository
) {
    @Transactional
    fun createBankAccount(encryptedPin: String, iv: String, accountId: Long, branchId: Long, userId: Long): CreatedBankAccount {

        // 사용자 정보와 계정정보가 일치한지 검증
        val user = getUserById(userId)

        if (user.accountId != accountId) {
            throw NotFoundException(message = "계정 정보가 일치하지 않습니다.")
        }

        // 유효 계정인지 검증
        val account = getAccountById(accountId)

        // 유효한 지점인지 검증
        val branch = getBankBranchById(branchId)

        // 이미 은행 계좌가 있는지 검증
        isAccountHasBankAccount(account.id)

        // PIN 번호와 함께 계정을 생성하여 저장합니다.
        val bankAccount = BankAccount(
            accountId = account.id,
            branchId = branch.id,
            accountNumber = generateAccountNumber(account, branch),
            password = encryptedPin,
            iv = iv,
            balance = 0
        )

        // 계좌 생성
        val createdBankAccount = bankAccountRepository.save(bankAccount)

        return CreatedBankAccount(
            userId = userId,
            accountId = createdBankAccount.accountId,
            branchId = createdBankAccount.branchId,
            bankAccountId = createdBankAccount.id,
            balance = createdBankAccount.balance,
            accountNumber = createdBankAccount.accountNumber
        )
    }

    fun getUserById(
        id: Long
    ): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(message = "User with ID $id not found") }
    }

    // 계좌 조회
    fun getBankAccount(
        bankAccountId: Long
    ): BankAccount {
        return bankAccountRepository.findById(bankAccountId)
            .orElseThrow { NotFoundException(message = "Bank account with ID $bankAccountId not found") }
    }

    fun getAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId)
            .orElseThrow { NotFoundException(message = "Bank branch with ID $accountId not found") }
    }

    fun getBankBranchById(branchId: Long): BankBranch {
        return bankBranchRepository.findById(branchId)
            .orElseThrow { NotFoundException(message = "Bank branch with ID $branchId not found") }
    }

    fun getBankAccountById(bankAccountId: Long): BankAccount {
        return bankAccountRepository.findById(bankAccountId)
            .orElseThrow { NotFoundException(message = "Bank account with ID $bankAccountId not found") }
    }

    // 계좌번호 생성
    private fun generateAccountNumber(account: Account, branch: BankBranch): String {
        val accountCode = account.id.toString().padStart(2, '0')
        val branchCode = branch.id.toString().padStart(2, '0')
        val randomCode = (10..99).random().toString()

        val accountNumber = "$accountCode$branchCode$randomCode"

        if (isAccountNumberExists(accountNumber)) Exception(ConflictException(message = "Account number $accountNumber already exists"))

        return accountNumber
    }

    // 중복된 계좌 생성을 방지하는 기능 추가
    fun isAccountNumberExists(accountNumber: String): Boolean {
        return bankAccountRepository.findByAccountNumber(accountNumber) != null
    }

    // 이미 Account 가 BankAccount 를 가지고 있는지 여부 반환하는 기능 추가
    fun isAccountHasBankAccount(accountId: Long): Boolean {

        val bankAccount: BankAccount? = bankAccountRepository.findByAccountId(accountId)

        if (bankAccount != null){
            Exception(ConflictException(message = "Account with ID $accountId already has bank account"))
        }

        return getBankAccountById(accountId) != null
    }

    data class CreatedBankAccount(
        val userId: Long,
        val accountId: Long,
        val branchId: Long,
        val bankAccountId: Long,
        val balance: Int,
        val accountNumber: String
    )
}