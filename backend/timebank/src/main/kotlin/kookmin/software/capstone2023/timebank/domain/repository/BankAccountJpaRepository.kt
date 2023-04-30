package kookmin.software.capstone2023.timebank.domain.repository
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
@Repository
interface BankAccountJpaRepository : JpaRepository<BankAccount, Long> {
    fun findByAccountId(accountId: Long): BankAccount?

    fun findByAccountNumber(accountNumber: String): BankAccount?
}
