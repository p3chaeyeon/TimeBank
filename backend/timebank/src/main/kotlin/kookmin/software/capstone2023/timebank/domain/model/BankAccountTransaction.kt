package kookmin.software.capstone2023.timebank.domain.model
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "bank_account_transaction")
data class BankAccountTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val bankAccountId: Long,

    @Enumerated(EnumType.STRING)
    val code: TransactionCode? = null,

    val amount: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.REQUESTED,

    val receiverAccountNumber: String? = null,

    val senderAccountNumber: String? = null,

    val balanceSnapshot: BigDecimal? = null,

    @Column(nullable = false)
    val transactionAt: LocalDateTime = LocalDateTime.now(),
) : BaseTimeEntity()
