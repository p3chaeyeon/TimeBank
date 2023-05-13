package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import kookmin.software.capstone2023.timebank.domain.model.AccountType

data class CurrentUserResponseData(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val account: AccountResponseData,
) {
    data class AccountResponseData(
        val id: Long,
        val type: AccountType,
        val name: String,
    )
}
