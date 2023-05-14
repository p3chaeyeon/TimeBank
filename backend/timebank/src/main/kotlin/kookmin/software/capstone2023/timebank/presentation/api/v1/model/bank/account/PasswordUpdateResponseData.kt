package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account

import java.time.LocalDateTime

data class PasswordUpdateResponseData(
    val bankAccountNumber: String, // 계좌 번호
    val updateAt: LocalDateTime, // 변경 일자
    val resResultCode: String, // 결과 코드
)
