package kookmin.software.capstone2023.timebank.application.service.auth.model

import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.domain.model.auth.AuthenticationType
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType

sealed class AuthenticationRequest(
    val type: AuthenticationType,
    open val accountType: AccountType? = null,
) {
    data class SocialAuthenticationRequest(
        val socialPlatformType: SocialPlatformType,
        val accessToken: String,
        override val accountType: AccountType?,
    ) : AuthenticationRequest(type = AuthenticationType.SOCIAL, accountType = accountType)

    data class PasswordAuthenticationRequest(
        val username: String,
        val password: String,
        override val accountType: AccountType?,
    ) : AuthenticationRequest(type = AuthenticationType.PASSWORD, accountType = accountType)
}
