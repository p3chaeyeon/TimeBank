package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import kookmin.software.capstone2023.timebank.domain.model.auth.AuthenticationType
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "user")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE id = ?")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    /**
     * 계정 ID
     */
    @Column(nullable = false, updatable = true)
    val accountId: Long,

    /**
     * 인증 타입
     */
    @Column(nullable = false, updatable = true, length = 20)
    @Enumerated(EnumType.STRING)
    val authenticationType: AuthenticationType,

    /**
     * 이름
     */
    @Column(nullable = false, updatable = true, length = 20)
    var name: String,

    /**
     * 전화번호
     */
    @Column(nullable = false, updatable = true, length = 20)
    var phoneNumber: String,

    /**
     * 성별
     */
    @Column(nullable = false, updatable = true, length = 10)
    @Enumerated(EnumType.STRING)
    var gender: Gender,

    /**
     * 생년월일
     */
    @Column(nullable = false, updatable = true)
    var birthday: LocalDate,

    /**
     * 마지막 로그인 시간 (UTC)
     */
    @Column(nullable = true, updatable = true)
    var lastLoginAt: LocalDateTime?,

    @Column(nullable = true, updatable = true)
    var deletedAt: LocalDateTime? = null,

) : BaseTimeEntity() {
    fun updateUserInfo(
        name: String,
        phoneNumber: String,
        gender: Gender,
        birthday: LocalDate,
    ) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.gender = gender
        this.birthday = birthday
    }

    fun updateLastLoginAt(loginAt: LocalDateTime) {
        this.lastLoginAt = loginAt
    }
}
