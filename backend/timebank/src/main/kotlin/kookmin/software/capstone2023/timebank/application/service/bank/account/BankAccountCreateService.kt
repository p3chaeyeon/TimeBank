package kookmin.software.capstone2023.timebank.application.service.bank.account

import kookmin.software.capstone2023.timebank.application.exception.ConflictException
import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.model.BankBranch
import kookmin.software.capstone2023.timebank.domain.model.OwnerType
import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.BankBranchJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class BankAccountCreateService(
    private val bankAcoountReadService: BankAccountReadService,
    private val bankAccountRepository: BankAccountJpaRepository,
    private val bankBranchJpaRepository: BankBranchJpaRepository,
    private val userRepository: UserJpaRepository,
) {
    @Transactional
    fun createBankAccount(
        encryptedPassword: String,
        iv: String,
        accountId: Long,
        branchId: Long,
        userId: Long,
    ): CreatedBankAccount {
        // 사용자 정보와 계정정보가 일치한지 검증
        val user = getUserById(userId)

        if (user.accountId != accountId) {
            throw NotFoundException(message = "계정 정보가 일치하지 않습니다.")
        }

        // 유효 계정인지 검증
        val account = bankAcoountReadService.getAccountById(accountId)

        // 유효한 지점인지 검증
        val branch = getBankBranchById(branchId)

        // 이미 은행 계좌가 있는지 검증
        checkAccountHasBankAccount(account.id)

        // PIN 번호와 함께 계정을 생성하여 저장합니다.
        val bankAccount = BankAccount(
            accountId = account.id,
            branchId = branch.id,
            accountNumber = generateAccountNumber(account, branch),
            password = encryptedPassword,
            ownerType = OwnerType.USER,
            iv = iv,
            balance = BigDecimal(0),
        )

        // 계좌 생성
        val createdBankAccount = bankAccountRepository.save(bankAccount)

        return CreatedBankAccount(
            userId = userId,
            accountId = createdBankAccount.accountId,
            branchId = createdBankAccount.branchId,
            bankAccountId = createdBankAccount.id,
            balance = createdBankAccount.balance,
            accountNumber = createdBankAccount.accountNumber,
        )
    }

    fun getUserById(
        id: Long,
    ): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(message = "찾으시는 유저 정보가 존재하지 않습니다.") }
    }

    fun getBankBranchById(branchId: Long): BankBranch {
        return bankBranchJpaRepository.findById(branchId)
            .orElseThrow { NotFoundException(message = "찾으시는 은행 지점이 존재하지 않습니다.") }
    }

    // 계좌번호 생성
    private fun generateAccountNumber(account: Account, branch: BankBranch): String {
        val accountCode = account.id.toString().padStart(2, '0')
        val branchCode = branch.id.toString().padStart(2, '0')
        val randomCode = (10..99).random().toString()

        val accountNumber = "$accountCode$branchCode$randomCode"

        if (bankAcoountReadService.isAccountNumberExists(accountNumber)) {
            throw ConflictException(message = "이미 사용 중인 계좌번호입니다.")
        }

        return accountNumber
    }

    // 이미 Account 가 BankAccount 를 가지고 있는지 여부 반환하는 기능 추가
    fun checkAccountHasBankAccount(accountId: Long) {
        val bankAccount: BankAccount? = bankAccountRepository.findByAccountId(accountId)
        if (bankAccount != null) {
            throw ConflictException(message = "이미 은행 계좌가 존재합니다.")
        }
    }

    data class CreatedBankAccount(
        val userId: Long,
        val accountId: Long,
        val branchId: Long,
        val bankAccountId: Long,
        val balance: BigDecimal,
        val accountNumber: String,
    )
}
