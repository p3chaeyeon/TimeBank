package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Table(name = "account")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE account SET deleted_at = now() WHERE id = ?")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, updatable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val type: AccountType,

    @Column(nullable = false, updatable = true, length = 20)
    var name: String,

    @Embedded
    var profile: AccountProfile? = null,

    @Column(nullable = true, updatable = true)
    var deletedAt: LocalDateTime? = null,
) : BaseTimeEntity() {
    fun updateName(name: String) {
        this.name = name
    }
}
