package kookmin.software.capstone2023.timebank.application.service.bank.account

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.model.OwnerType
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class BankAccountReadService(
    private val bankAccountRepository: BankAccountJpaRepository,
    private val accountRepository: AccountJpaRepository,
) {
    // 은행 계좌 조회
    fun readBankAccountByAccountNumber(
        accountId: Long,
        bankAccountNumber: String,
    ): ReadedBankAccount {
        // 은행 계좌가 존재 하는지 확인. 없으면 404 에러
        val bankAccount: BankAccount = getBankAccountByBankAccountNumber(bankAccountNumber)

        // 소유주 여부 조회
        val isOwner: Boolean = isOwnerAccountOfBankAccount(accountId, bankAccount)

        val readedBankAccount: ReadedBankAccount = ReadedBankAccount(
            ownerName = bankAccount.ownerName,
            ownerType = bankAccount.ownerType,
            bankAccountNumber = bankAccount.accountNumber,
        )

        if (isOwner) { // 소유주라면
            readedBankAccount.bankAccountId = bankAccount.id // 은행 계좌 id
            readedBankAccount.branchId = bankAccount.branchId // 지점 id
            readedBankAccount.balance = bankAccount.balance // 잔액
            readedBankAccount.createdAt = bankAccount.createdAt // 생성일자
        }

        return readedBankAccount
    }

    // 계정이 가지는 모든 은행 계좌 조회
    fun readBankAccountsByAccountId(
        accountId: Long,
    ): List<ReadedBankAccount> {

        // 계정이 존재하는지 확인
        validateAccountExists(accountId)

        // 계정이 가지는 모든 은행 계좌 조회
        bankAccountRepository.findAllByAccountId(accountId).map {
            ReadedBankAccount(
                bankAccountId = it.id,
                branchId = it.branchId,
                balance = it.balance,
                createdAt = it.createdAt,
                ownerName = it.ownerName,
                ownerType = it.ownerType,
                bankAccountNumber = it.accountNumber,
            )
        }.let {
            return it
        }
    }

    fun getBankAccountByBankAccountNumber(bankAccountNumber: String): BankAccount {
        return bankAccountRepository.findByAccountNumber(bankAccountNumber)
            ?: throw NotFoundException(message = "존재하지 않는 은행 계좌입니다.")
    }

    // 계정 존재 여부 조회
    fun validateAccountExists(accountId: Long) {
        if (!accountRepository.existsById(accountId)) {
            throw NotFoundException(message = "존재하지 않는 계정입니다.")
        }
    }

    // 계좌 유무 조회
    fun isBankAccountNumberExists(accountNumber: String): Boolean {
        return bankAccountRepository.findByAccountNumber(accountNumber) != null
    }

    fun isOwnerAccountOfBankAccount(accountId: Long, bankAccount: BankAccount): Boolean {
        return bankAccount.accountId == accountId
    }

    fun validateAccountIsBankAccountOwner(accountId: Long, bankAccountNumber: String) {

        // 계정이 존재하는지 확인
        val bankAccount: BankAccount = getBankAccountByBankAccountNumber(bankAccountNumber)

        // 계정이 소유한 은행 계좌인지 확인
        if (!isOwnerAccountOfBankAccount(accountId, bankAccount)) {
            throw UnauthorizedException(message = "접근 권한이 없는 은행 계좌입니다.")
        }
    }

    // 은행 계좌 잔액, 생성 일자, ownerType, branchId, 계좌 번호(필수), 계좌 소유주명(필수) 반환
    data class ReadedBankAccount(
        var bankAccountId: Long? = null, // 은행 계좌 id
        var branchId: Long? = null, // 지점 id
        var balance: BigDecimal? = null, // 잔액
        var createdAt: LocalDateTime? = null, // 개설 일자
        val ownerName: String, // 소유주명
        val ownerType: OwnerType, // 소유주 타입
        val bankAccountNumber: String, // 계좌 번호
    )
}
// End of BankAccountReadService.kt