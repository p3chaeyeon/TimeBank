package kookmin.software.capstone2023.timebank.domain.repository

import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BankAccountTransactionJpaRepository : JpaRepository<BankAccountTransaction, Long> {

    // 모든 계좌 이체 내역 조회 (페이징)
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<BankAccountTransaction>

    // 특정 은행 계좌 id에 대한 계좌 이체 내역 조회(페이징)
    fun findAllByBankAccountIdOrderByCreatedAtDesc(bankAccountId: Long, pageable: Pageable): Page<BankAccountTransaction>
}