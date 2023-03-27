package kookmin.software.capstone2023.timebank.domain.repository

import kookmin.software.capstone2023.timebank.domain.model.auth.SocialAuthentication
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialAuthenticationJpaRepository : JpaRepository<SocialAuthentication, Long> {
    fun findByPlatformTypeAndPlatformUserId(
        platformType: SocialPlatformType,
        platformUserId: String
    ): SocialAuthentication?
}