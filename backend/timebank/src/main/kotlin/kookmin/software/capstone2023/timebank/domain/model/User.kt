package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*
import kookmin.software.capstone2023.timebank.domain.model.auth.AuthenticationType
import java.time.LocalDateTime

@Entity
@Table(name = "user")
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
    val name: String,

    /**
     * 전화번호
     */
    @Column(nullable = false, updatable = true, length = 20)
    val phoneNumber: String,

    /**
     * 마지막 로그인 시간 (UTC)
     */
    @Column(nullable = true, updatable = true)
    var lastLoginAt: LocalDateTime?,
) : BaseTimeEntity() {
    fun updateLastLoginAt(loginAt: LocalDateTime) {
        this.lastLoginAt = loginAt
    }
}