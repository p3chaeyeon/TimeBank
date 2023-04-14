package kookmin.software.capstone2023.timebank.application.service.bank.account

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankAccountReadService(
    private val userRepository: UserJpaRepository,
    private val accountRepository: AccountJpaRepository,
    private val bankAccountRepository: BankAccountJpaRepository,
) {

    // 은행 계좌 조회
    fun readBankAccount(
        userId: Long, // 유저 id
        accountId: Long, // 계정 id
        bankAccountId: Long, // 은행 계좌 id
    ): ReadedBankAccount {
        /*
        유저가 가진 계정id와 요청에 있는 계정id가 일치하는지 확인.
         */
        isUserHasAccount(userId, accountId)
        /*
         유효한 계정 id인지 확인.
         */
        isAccountValid(accountId)
        /*
         은행 계좌 정보 조회
         */
        val bankAccount = getBankAccount(bankAccountId)
        /*
        은행 계좌 정보 반환, ReadedBankAccount 객체로 반환
         */
        return ReadedBankAccount(
            userId = userId,
            accountId = bankAccount.accountId,
            branchId = bankAccount.branchId,
            bankAccountId = bankAccount.id,
            balance = bankAccount.balance,
            accountNumber = bankAccount.accountNumber,
        )
    }

    fun readBankAccountByBankAccountNumber(
        userId: Long,
        accountId: Long,
        bankAccountNumber: String,
    ): ReadedBankAccount {
        /*
        유저가 가진 계정id와 요청에 있는 계정id가 일치하는지 확인.
         */
        isUserHasAccount(userId, accountId)
        /*
         유효한 계정 id인지 확인.
         */
        isAccountValid(accountId)
        /*
         은행 계좌 정보 조회
         */
        val bankAccount = getBankAccountByAccountNumber(bankAccountNumber)

        return ReadedBankAccount(
            userId = userId,
            accountId = bankAccount.accountId,
            branchId = bankAccount.branchId,
            bankAccountId = bankAccount.id,
            balance = bankAccount.balance,
            accountNumber = bankAccount.accountNumber,
        )
    }

    // 유저가 가진 계정 id와 요청에 있는 계정 id가 일치하는지 확인.
    private fun isUserHasAccount(
        userId: Long,
        accountId: Long,
    ): Boolean {
        val user = userRepository.getUserById(userId)

        if (user == null) {
            throw NotFoundException(message = "존재하지 않는 유저 정보입니다")
        }

        return user.accountId == accountId
    }

    // 유효한 계정 id인지 확인.
    private fun isAccountValid(
        accountId: Long,
    ): Boolean {
        val account = accountRepository.getAccountById(accountId)

        if (account == null) {
            throw NotFoundException(message = "찾으시는 계좌가 존재하지 않습니다.")
        }

        return account.id == accountId
    }

    fun getAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId)
            .orElseThrow { NotFoundException(message = "찾으시는 계정이 존재하지 않습니다.") }
    }

    fun getBankAccount(bankAccountId: Long): BankAccount {
        return bankAccountRepository.findById(bankAccountId)
            .orElseThrow { NotFoundException(message = "찾으시는 계좌가 존재하지 않습니다.") }
    }

    fun getBankAccountByAccountNumber(accountNumber: String): BankAccount {
        return bankAccountRepository.findByAccountNumber(accountNumber)
            ?: throw NotFoundException(message = "찾으시는 계좌가 존재하지 않습니다.")
    }

    fun getBankAccountByAccountNumberAndAccountId(accountNumber: String, accountId: Long): BankAccount {
        val bankAccount = getBankAccountByAccountNumber(accountNumber)
        if (accountId != bankAccount.accountId) {
            throw UnauthorizedException(message = "계좌 소유주가 일치하지 않습니다.")
        }
        return bankAccount
    }

    // 중복된 계좌 생성을 방지하는 기능 추가
    fun isAccountNumberExists(accountNumber: String): Boolean {
        return bankAccountRepository.findByAccountNumber(accountNumber) != null
    }

    fun isBankAccountExists(bankAccountId: Long): Boolean {
        return bankAccountRepository.findById(bankAccountId).isPresent
    }

    fun validateBankAccountPasswordCorrect(bankAccountId: Long, password: String) {
        val bankAccount = bankAccountRepository.findById(bankAccountId)
            .orElseThrow { NotFoundException(message = "찾으시는 계좌가 존재하지 않습니다.") }

        if (bankAccount.password != password) {
            throw UnauthorizedException(message = "비밀번호가 일치하지 않습니다.")
        }
    }

    // 계좌 잔액을 확인하는 메소드
    fun validateBankAccountBalanceEnough(
        bankAccountId: Long,
        amount: BigDecimal,
    ) {
        val bankAccount = getBankAccount(bankAccountId)
        if (bankAccount.balance.compareTo(BigDecimal.ZERO) < 0 || bankAccount.balance.compareTo(amount) < 0) {
            throw UnauthorizedException(message = "잔액이 부족합니다.")
        }
    }

    data class ReadedBankAccount(
        val userId: Long,
        val accountId: Long,
        val branchId: Long,
        val bankAccountId: Long,
        val balance: BigDecimal,
        val accountNumber: String,
    )
}
