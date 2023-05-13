package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.AccountType

data class AccountResponseData(
    val id: Long,
    val type: AccountType,
    val name: String,
    val profile: ProfileResponseData?,
) {
    data class ProfileResponseData(
        val nickname: String,
        val imageUrl: String,
    )

    companion object {
        fun fromDomain(account: Account): AccountResponseData {
            return AccountResponseData(
                id = account.id,
                type = account.type,
                name = account.name,
                profile = account.profile?.let {
                    ProfileResponseData(
                        nickname = it.nickname,
                        imageUrl = it.imageUrl,
                    )
                },
            )
        }
    }
}