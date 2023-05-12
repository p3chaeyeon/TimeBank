package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account
import jakarta.validation.constraints.NotBlank

data class BankAccountCreateRequestData(

    @field:NotBlank(message = "생성하려는 은행 계정의 패스워드를 보내주세요")
    val password: String,

)
