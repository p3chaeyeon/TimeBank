package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account

data class PasswordUpdateRequestData(
    val beforePassword: String, // 이전 비밀번호
    val afterPassword: String, // 변경할 비밀번호
    val bankAccountNumber: String, // 계좌 번호
)
