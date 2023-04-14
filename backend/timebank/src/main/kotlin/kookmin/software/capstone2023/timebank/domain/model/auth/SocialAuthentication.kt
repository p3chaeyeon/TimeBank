package kookmin.software.capstone2023.timebank.domain.model.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
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
