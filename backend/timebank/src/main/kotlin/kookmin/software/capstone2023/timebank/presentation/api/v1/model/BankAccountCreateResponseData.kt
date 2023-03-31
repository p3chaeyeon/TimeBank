package kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model

import java.math.BigDecimal

data class BankAccountCreateResponseData (
    val balance: BigDecimal,
    val accountNumber: String,
    val bankAccountId: Long
)