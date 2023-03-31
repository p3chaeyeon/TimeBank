package kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model

data class BankAccountCreateResponseData (
    val balance: Int,
    val accountNumber: String,
    val bankAccountId: Long
)