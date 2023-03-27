package kookmin.software.capstone2023.timebank.domain.model.auth

import jakarta.persistence.*
import kookmin.software.capstone2023.timebank.domain.model.BaseTimeEntity

@Entity
@Table(name = "authentication_social")
class SocialAuthentication(
    @Id
    @Column(nullable = false, updatable = false)
    val userId: Long,

    @Column(nullable = false, updatable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val platformType: SocialPlatformType,

    @Column(nullable = false, updatable = true, length = 100)
    val platformUserId: String,
) : BaseTimeEntity()