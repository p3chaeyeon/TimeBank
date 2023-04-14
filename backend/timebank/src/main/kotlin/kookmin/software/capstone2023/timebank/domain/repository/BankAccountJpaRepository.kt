package kookmin.software.capstone2023.timebank.domain.repository
import jakarta.persistence.LockModeType
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository


@Repository
interface BankAccountJpaRepository : JpaRepository<BankAccount, Long> {

    fun findByAccountId(accountId: Long): BankAccount?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByAccountNumber(accountNumber: String): BankAccount?
}