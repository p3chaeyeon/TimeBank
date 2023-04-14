package kookmin.software.capstone2023.timebank.domain.model
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "BankAccountTransaction")
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
):BaseTimeEntity()