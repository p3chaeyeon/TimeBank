package kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import kookmin.software.capstone2023.timebank.application.service.auth.model.AuthenticationRequest
import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType

data class BankAccountCreateRequestData(

    val branchId: Long,

    @field:NotBlank(message = "생성하려는 은행 계정의 패스워드를 보내주세요")
    val password: String,

    @field:NotBlank(message = "생성하려는 은행 계정의 패스워드의 iv를 보내주세요")
    val iv: String
)