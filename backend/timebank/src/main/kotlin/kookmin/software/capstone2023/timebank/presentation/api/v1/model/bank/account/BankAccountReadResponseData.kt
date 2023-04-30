package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account

import java.math.BigDecimal

data class BankAccountReadResponseData(
    val balance: BigDecimal? = null,
    val accountNumber: String,
    val bankAccountId: Long? = null,
)
