package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import kookmin.software.capstone2023.timebank.application.service.auth.model.AuthenticationRequest
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType

sealed class UserRegisterRequestData {
    data class SocialUserRegisterRequestData(
        val socialPlatformType: SocialPlatformType,

        @field:NotBlank(message = "액세스 토큰은 필수입니다.")
        val accessToken: String,

        @field:NotBlank(message = "이름을 입력해주세요.")
        @field:Max(value = 20, message = "이름은 20자 이하로 입력해주세요.")
        val name: String,

        @field:NotBlank(message = "전화번호를 입력해주세요.")
        @field:Pattern(regexp = "^[0-9]{11}$", message = "전화번호는 11자리 숫자만 입력 가능합니다.")
        val phoneNumber: String,
    ) : UserRegisterRequestData() {
        fun toAuthenticationRequest() = AuthenticationRequest.SocialAuthenticationRequest(
            socialPlatformType = socialPlatformType,
            accessToken = accessToken,
        )
    }

    data class UserPasswordRegisterRequestData(
        @field:NotBlank(message = "아이디는 필수입니다.")
        val username: String,

        @field:NotBlank(message = "비밀번호는 필수입니다.")
        val password: String,

        @field:NotBlank(message = "이름을 입력해주세요.")
        @field:Max(value = 20, message = "이름은 20자 이하로 입력해주세요.")
        val name: String,

        @field:NotBlank(message = "전화번호를 입력해주세요.")
        @field:Pattern(regexp = "^[0-9]{11}$", message = "전화번호는 11자리 숫자만 입력 가능합니다.")
        val phoneNumber: String,
    ) : UserRegisterRequestData() {
        fun toAuthenticationRequest() = AuthenticationRequest.PasswordAuthenticationRequest(
            username = username,
            password = password,
        )
    }
}
