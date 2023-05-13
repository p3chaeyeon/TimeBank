package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account

import jakarta.validation.constraints.NotBlank
data class PasswordVerificationRequestData(

    @field:NotBlank(message = "은행 계좌 번호를 보내주세요")
    val bankAccountNumber: String,

    @field:NotBlank(message = "은행 계좌 패스워드를 보내주세요")
    val password: String,
)
