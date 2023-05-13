package kookmin.software.capstone2023.timebank.presentation.api.v1.model

data class CurrentUserResponseData(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val account: AccountResponseData,
)
