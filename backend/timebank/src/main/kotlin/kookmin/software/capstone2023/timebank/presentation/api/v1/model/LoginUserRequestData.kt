package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import jakarta.validation.constraints.NotBlank
import kookmin.software.capstone2023.timebank.domain.model.SocialPlatformType

sealed class LoginUserRequestData {
    data class SocialLoginUserRequestData(
        val provider: SocialPlatformType,
        @field:NotBlank(message = "액세스 토큰은 필수입니다.")
        val accessToken: String,
    ) : LoginUserRequestData()
}
