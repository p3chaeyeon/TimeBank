package kookmin.software.capstone2023.timebank.application.configuration

import kookmin.software.capstone2023.timebank.application.service.auth.DefaultUserAuthenticator
import kookmin.software.capstone2023.timebank.application.service.auth.TestEnvironmentUserAuthenticator
import kookmin.software.capstone2023.timebank.application.service.auth.UserAuthenticator
import kookmin.software.capstone2023.timebank.application.service.auth.token.AccessTokenService
import kookmin.software.capstone2023.timebank.core.logger
import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class UserAuthenticatorConfiguration(
    private val authenticationTestProperties: AuthenticationTestProperties,
) {
    private val logger by logger()

    @Bean
    @Profile("dev")
    fun devEnvironmentUserAuthenticator(
        accessTokenService: AccessTokenService,
        userJpaRepository: UserJpaRepository,
        accountJpaRepository: AccountJpaRepository,
    ): UserAuthenticator {
        // 인증 테스트가 활성화되어 있으면 테스트 환경 사용자 인증기를 반환한다.
        if (authenticationTestProperties.enabled) {
            val accountType = AccountType.valueOf(authenticationTestProperties.accountType)

            logger.info("Test environment user authenticator is enabled.")
            logger.info("userId: ${authenticationTestProperties.userId}")
            logger.info("accountId: ${authenticationTestProperties.accountId}")
            logger.info("accountType: $accountType")

            return TestEnvironmentUserAuthenticator(
                userId = authenticationTestProperties.userId,
                accountId = authenticationTestProperties.accountId,
                accountType = accountType,
            )
        }

        logger.info("Test environment user authenticator is disabled. (default user authenticator is enabled.")

        return DefaultUserAuthenticator(
            accessTokenService = accessTokenService,
            userJpaRepository = userJpaRepository,
            accountJpaRepository = accountJpaRepository,
        )
    }

    @Bean
    @Profile("!dev")
    fun defaultUserAuthenticator(
        accessTokenService: AccessTokenService,
        userJpaRepository: UserJpaRepository,
        accountJpaRepository: AccountJpaRepository,
    ): UserAuthenticator {
        return DefaultUserAuthenticator(
            accessTokenService = accessTokenService,
            userJpaRepository = userJpaRepository,
            accountJpaRepository = accountJpaRepository,
        )
    }
}