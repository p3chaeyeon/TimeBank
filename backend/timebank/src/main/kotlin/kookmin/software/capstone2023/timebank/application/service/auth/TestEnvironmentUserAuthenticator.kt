package kookmin.software.capstone2023.timebank.application.service.auth

import kookmin.software.capstone2023.timebank.domain.model.AccountType

class TestEnvironmentUserAuthenticator(
    private val userId: Long,
    private val accountId: Long,
    private val accountType: AccountType,
) : UserAuthenticator {
    override fun authenticate(accessToken: String): UserAuthenticator.AuthenticationData {
        return UserAuthenticator.AuthenticationData(
            userId = userId,
            accountId = accountId,
            accountType = accountType,
        )
    }
}