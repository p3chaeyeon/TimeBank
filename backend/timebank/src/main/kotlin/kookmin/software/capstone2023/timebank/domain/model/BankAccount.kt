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
@Table(name = "bank_account")
data class BankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val accountId: Long,

    @Column(nullable = false)
    var branchId: Long,

    @Column(nullable = false, updatable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val ownerType: OwnerType,

    @Column(nullable = false, updatable = true)
    val ownerName: String,

    @Column(nullable = false)
    val accountNumber: String,

    @Column(nullable = true)
    var password: String,

    @Column(nullable = false)
    var balance: BigDecimal,

    @Column(nullable = true)
    val deletedAt: LocalDateTime? = null,
) : BaseTimeEntity()
