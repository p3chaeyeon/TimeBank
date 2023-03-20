package kookmin.software.capstone2023.timebank.application.service.auth.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kookmin.software.capstone2023.timebank.application.configuration.AccessTokenProperties
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserAccessTokenIssueService(
    private val accessTokenProperties: AccessTokenProperties,
) {
    fun issue(userId: Long, expiresAt: Instant): String {
        val signAlgorithm = Algorithm.HMAC256(accessTokenProperties.secretKey)

        return JWT.create()
            .withClaim("userId", userId)
            .withExpiresAt(expiresAt)
            .sign(signAlgorithm)
    }
}