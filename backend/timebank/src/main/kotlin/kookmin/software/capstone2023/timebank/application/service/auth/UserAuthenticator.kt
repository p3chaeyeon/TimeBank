package kookmin.software.capstone2023.timebank.application.service.auth

import kookmin.software.capstone2023.timebank.domain.model.AccountType

interface UserAuthenticator {
    data class AuthenticationData(
        val userId: Long,
        val accountId: Long,
        val accountType: AccountType,
    )

    fun authenticate(accessToken: String): AuthenticationData
}