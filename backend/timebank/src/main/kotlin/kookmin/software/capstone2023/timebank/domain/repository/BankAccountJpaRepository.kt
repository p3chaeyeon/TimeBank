package kookmin.software.capstone2023.timebank.domain.repository

import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountJpaRepository : JpaRepository<BankAccount, Long> {
    fun findAll(spec: Specification<BankAccount>, pageable: Pageable): Page<BankAccount>
    fun findByAccountNumber(accountNumber: String): BankAccount?
    fun findAllByAccountId(accountId: Long): List<BankAccount>
}
