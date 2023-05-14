package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import kookmin.software.capstone2023.timebank.domain.model.Gender
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class UserUpdateRequestData(
    @field:NotBlank(message = "이름을 입력해주세요.")
    @field:Length(max = 20, message = "이름은 20자 이하로 입력해주세요.")
    val name: String,

    @field:NotBlank(message = "전화번호를 입력해주세요.")
    @field:Pattern(regexp = "^[0-9]{11}$", message = "전화번호는 11자리 숫자만 입력 가능합니다.")
    val phoneNumber: String,

    val gender: Gender,

    val birthday: LocalDate,
)
