package kookmin.software.capstone2023.timebank.application.service.bank.account

import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BankAccountUpdateService(
    private val bankAccountRepository: BankAccountJpaRepository,
    private val bankAccountReadService: BankAccountReadService,
) {
    @Transactional
    fun updateBankAccountPassword(
        accountId: Long,
        accountNumber: String,
        beforePassword: String,
        afterPassword: String,
    ): UpdatedBankAccount {
        
        // 계정에 권한이 있는지 검증
        bankAccountReadService.validateAccountIsBankAccountOwner(accountId, accountNumber)
        
        // 비밀번호 검증
        bankAccountReadService.validateBankAccountPassword(accountNumber, beforePassword)
        
        val bankAccount: BankAccount = bankAccountReadService.getBankAccountByBankAccountNumber(accountNumber)
        
        bankAccount.password = afterPassword
        
        bankAccountRepository.save(bankAccount)
        
        return UpdatedBankAccount(
            bankAccountNumber = bankAccount.accountNumber,
            updatedAt = bankAccount.updatedAt,
        )
    }
    
    data class UpdatedBankAccount(
        val bankAccountNumber: String,
        val updatedAt: LocalDateTime,
    )
}
