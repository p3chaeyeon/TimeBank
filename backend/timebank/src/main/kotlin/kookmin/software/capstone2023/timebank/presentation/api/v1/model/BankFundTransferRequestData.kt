package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import java.math.BigDecimal

data class BankFundTransferRequestData(
    val senderBankAccountNumber: String,
    val receiverBankAccountNumber: String,
    val amount: BigDecimal,
    val password: String
)
