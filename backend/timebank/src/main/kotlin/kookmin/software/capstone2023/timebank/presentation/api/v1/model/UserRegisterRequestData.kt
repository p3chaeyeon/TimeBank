package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import kookmin.software.capstone2023.timebank.application.service.auth.model.AuthenticationRequest
import kookmin.software.capstone2023.timebank.domain.model.Gender
import kookmin.software.capstone2023.timebank.domain.model.auth.AuthenticationType
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "authenticationType",
)
sealed class UserRegisterRequestData(
    open val authenticationType: AuthenticationType,
    open val name: String,
    open val phoneNumber: String,
    open val gender: Gender,
    open val birthday: LocalDate,
) {
    open fun toAuthenticationRequest(): AuthenticationRequest {
        return when (this) {
            is SocialUserRegisterRequestData -> toAuthenticationRequest()
            is UserPasswordRegisterRequestData -> toAuthenticationRequest()
        }
    }

    @JsonTypeName("social")
    data class SocialUserRegisterRequestData(
        val socialPlatformType: SocialPlatformType,

        @field:NotBlank(message = "액세스 토큰은 필수입니다.")
        val accessToken: String,

        @field:NotBlank(message = "이름을 입력해주세요.")
        @field:Length(max = 20, message = "이름은 20자 이하로 입력해주세요.")
        override val name: String,

        @field:NotBlank(message = "전화번호를 입력해주세요.")
        @field:Pattern(regexp = "^[0-9]{11}$", message = "전화번호는 11자리 숫자만 입력 가능합니다.")
        override val phoneNumber: String,

        override val gender: Gender,

        override val birthday: LocalDate,
    ) : UserRegisterRequestData(AuthenticationType.SOCIAL, name, phoneNumber, gender, birthday) {
        override fun toAuthenticationRequest() = AuthenticationRequest.SocialAuthenticationRequest(
            socialPlatformType = socialPlatformType,
            accessToken = accessToken,
            accountType = null,
        )
    }

    @JsonTypeName("password")
    data class UserPasswordRegisterRequestData(
        @field:NotBlank(message = "아이디는 필수입니다.")
        val username: String,

        @field:NotBlank(message = "비밀번호는 필수입니다.")
        val password: String,

        @field:NotBlank(message = "이름을 입력해주세요.")
        @field:Length(max = 20, message = "이름은 20자 이하로 입력해주세요.")
        override val name: String,

        @field:NotBlank(message = "전화번호를 입력해주세요.")
        @field:Pattern(regexp = "^[0-9]{11}$", message = "전화번호는 11자리 숫자만 입력 가능합니다.")
        override val phoneNumber: String,

        override val gender: Gender,

        override val birthday: LocalDate,
    ) : UserRegisterRequestData(AuthenticationType.PASSWORD, name, phoneNumber, gender, birthday) {
        override fun toAuthenticationRequest() = AuthenticationRequest.PasswordAuthenticationRequest(
            username = username,
            password = password,
            accountType = null,
        )
    }
}
