package kookmin.software.capstone2023.timebank.application.service.auth.model

import kookmin.software.capstone2023.timebank.domain.model.auth.AuthenticationType
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType

sealed class AuthenticationRequest(
    val type: AuthenticationType,
) {
    data class SocialAuthenticationRequest(
        val socialPlatformType: SocialPlatformType,
        val accessToken: String,
    ) : AuthenticationRequest(type = AuthenticationType.SOCIAL)

    data class PasswordAuthenticationRequest(
        val username: String,
        val password: String,
    ) : AuthenticationRequest(type = AuthenticationType.PASSWORD)
}
