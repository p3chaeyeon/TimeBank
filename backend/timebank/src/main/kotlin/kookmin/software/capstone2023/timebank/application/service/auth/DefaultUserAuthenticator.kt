package kookmin.software.capstone2023.timebank.application.service.auth

import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.application.service.auth.token.AccessTokenService
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull

class DefaultUserAuthenticator(
    private val accessTokenService: AccessTokenService,
    private val userJpaRepository: UserJpaRepository,
    private val accountJpaRepository: AccountJpaRepository,
) : UserAuthenticator {
    override fun authenticate(accessToken: String): UserAuthenticator.AuthenticationData {
        val claims = accessTokenService.verify(accessToken)

        val user = userJpaRepository.findByIdOrNull(claims.userId)
            ?: throw UnauthorizedException(message = null)

        val account = accountJpaRepository.findByIdOrNull(claims.accountId)
            ?: throw UnauthorizedException(message = null)

        if (user.account.id != account.id) {
            throw UnauthorizedException(message = null)
        }

        return UserAuthenticator.AuthenticationData(
            userId = user.id,
            accountId = account.id,
            accountType = account.type,
        )
    }
}
