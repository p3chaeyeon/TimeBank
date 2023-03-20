package kookmin.software.capstone2023.timebank.application.service.auth

import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.application.service.auth.token.UserAccessTokenIssueService
import kookmin.software.capstone2023.timebank.domain.model.SocialPlatformType
import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class UserLoginService(
    private val socialUserFindService: SocialUserFindService,
    private val userAccessTokenIssueService: UserAccessTokenIssueService,
    private val userJpaRepository: UserJpaRepository,
) {
    data class UserLoginData(
        val accessToken: String,
    )

    @Transactional
    fun socialLogin(
        provider: SocialPlatformType,
        accessToken: String
    ): UserLoginData {
        val socialUser = socialUserFindService.getSocialUser(
            platformType = provider,
            accessToken = accessToken,
        )

        val user: User = userJpaRepository.findBySocialLoginProviderAndSocialUserId(
            socialLoginProvider = provider,
            socialUserId = socialUser.id,
        ) ?: throw UnauthorizedException(message = "등록되지 않은 사용자입니다.")

        val userAccessToken = userAccessTokenIssueService.issue(
            userId = user.id,
            expiresAt = Instant.now().plus(7, ChronoUnit.DAYS),
        )

        user.updateLastLoginAt(
            loginAt = LocalDateTime.now(),
        )

        return UserLoginData(
            accessToken = userAccessToken,
        )
    }
}