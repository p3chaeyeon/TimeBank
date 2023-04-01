package kookmin.software.capstone2023.timebank.application.configuration

import kookmin.software.capstone2023.timebank.domain.model.AccountType

data class UserProperties(
    val id: Long,
    val accountId: Long,
    val accountType: AccountType,
)
