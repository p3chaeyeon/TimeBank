package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import java.math.BigDecimal

data class BankAccountReadResponseData (
    val balance: BigDecimal,
    val accountNumber: String,
    val bankAccountId: Long
)