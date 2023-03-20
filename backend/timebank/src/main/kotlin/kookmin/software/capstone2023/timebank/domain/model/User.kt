package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*
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
     * 소셜 로그인 제공자
     */
    @Column(nullable = true, updatable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val socialLoginProvider: SocialPlatformType?,

    /**
     * 소셜 플랫폼 유저 ID
     */
    @Column(nullable = true, updatable = false, length = 20)
    val socialUserId: String?,

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