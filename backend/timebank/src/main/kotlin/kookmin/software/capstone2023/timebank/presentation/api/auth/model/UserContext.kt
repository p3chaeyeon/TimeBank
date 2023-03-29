package kookmin.software.capstone2023.timebank.presentation.api.auth.model

import kookmin.software.capstone2023.timebank.domain.model.AccountType

data class UserContext(
    val userId: Long,
    val accountId: Long,
    val accountType: AccountType,
) {
}
