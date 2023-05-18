package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "bank_account")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE account SET deleted_at = now() WHERE id = ?")
data class BankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

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

    @ManyToOne
    @JoinColumn(
        name = "account_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val account: Account,

    @ManyToOne
    @JoinColumn(
        name = "branch_id",
        nullable = false,
        updatable = true,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val branch: BankBranch,
) : BaseTimeEntity()
