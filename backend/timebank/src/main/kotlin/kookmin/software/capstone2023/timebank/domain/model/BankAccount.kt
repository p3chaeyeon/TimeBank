package kookmin.software.capstone2023.timebank.domain.model
import jakarta.persistence.*
import kookmin.software.capstone2023.timebank.domain.model.BankBranch
import kookmin.software.capstone2023.timebank.domain.model.OwnerType
import kookmin.software.capstone2023.timebank.domain.model.User
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "BankAccount")
data class BankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val accountId: Long,

    var branchId: Long,

    @Column(nullable = false, updatable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val ownerType: OwnerType,

    @Column(nullable = false)
    val accountNumber: String,

    @Column(nullable = true)
    var password: String,

    @Column(nullable = true)
    var iv: String,

    @Column(nullable = false)
    var balance: BigDecimal,

    @Column(nullable = true)
    val deletedAt: LocalDateTime? = null
):BaseTimeEntity()