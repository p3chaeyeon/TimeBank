package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import jakarta.validation.constraints.NotBlank
import kookmin.software.capstone2023.timebank.application.service.auth.model.AuthenticationRequest
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType

sealed class UserLoginRequestData {
    data class SocialLoginRequestData(
        val socialPlatformType: SocialPlatformType,

        @field:NotBlank(message = "액세스 토큰은 필수입니다.")
        val accessToken: String,
    ) : UserLoginRequestData() {
        fun toAuthenticationRequest() = AuthenticationRequest.SocialAuthenticationRequest(
            socialPlatformType = socialPlatformType,
            accessToken = accessToken,
        )
    }

    data class PasswordLoginRequestData(
        @field:NotBlank(message = "아이디는 필수입니다.")
        val username: String,

        @field:NotBlank(message = "비밀번호는 필수입니다.")
        val password: String,
    ) : UserLoginRequestData() {
        fun toAuthenticationRequest() = AuthenticationRequest.PasswordAuthenticationRequest(
            username = username,
            password = password,
        )
    }
}
