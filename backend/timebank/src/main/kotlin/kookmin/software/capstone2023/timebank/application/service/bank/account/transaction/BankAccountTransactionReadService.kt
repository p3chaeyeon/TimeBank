package kookmin.software.capstone2023.timebank.application.service.bank.account.transaction
import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountTransactionJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BankAccountTransactionReadService(
    private val bankAccountReadService: BankAccountReadService,
    private val bankAccountTransactionRepository: BankAccountTransactionJpaRepository,
) {
    // 계좌 이체 내역 조회
    fun getBankAccountTransactionById(bankAccountTransactionId: Long): BankAccountTransaction {
        return bankAccountTransactionRepository.findById(bankAccountTransactionId)
            .orElseThrow { NotFoundException(message = "계좌 이체 내역을 찾을 수 없습니다.") }
    }

    // 특정 은행 계좌의 모든 계좌 이체 내역 Paging 해서 조회
    fun getBankAccountTransactionByAccountId(
        accountId: Long,
        pageable: Pageable,
    ): Page<BankAccountTransaction> {
        return bankAccountTransactionRepository.findAllByBankAccountIdOrderByCreatedAtDesc(accountId, pageable)
    }

    // 특정 은행 계좌의 모든 계좌 이체 내역 Paging 해서 조회 (은행 계좌로 조회)
    fun getBankAccountTransactionByAccountNumber(
        accountNumber: String,
        pageable: Pageable,
    ): Page<BankAccountTransaction> {
        val bankAccount: BankAccount = bankAccountReadService.getBankAccountByBankAccountNumber(accountNumber)
        return getBankAccountTransactionByAccountId(bankAccount.id, pageable)
    }
}
