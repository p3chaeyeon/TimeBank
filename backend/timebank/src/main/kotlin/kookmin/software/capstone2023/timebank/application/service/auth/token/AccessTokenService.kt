package kookmin.software.capstone2023.timebank.application.service.auth.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import kookmin.software.capstone2023.timebank.application.configuration.AccessTokenProperties
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AccessTokenService(
    private val accessTokenProperties: AccessTokenProperties,
) {
    fun issue(userId: Long, accountId: Long, expiresAt: Instant?): String {
        val algorithm = Algorithm.HMAC256(accessTokenProperties.secretKey)

        var tokenBuilder = JWT.create()
            .withClaim("userId", userId)
            .withClaim("accountId", accountId)

        if (expiresAt != null) {
            tokenBuilder = tokenBuilder.withExpiresAt(expiresAt)
        }

        return tokenBuilder.sign(algorithm)
    }

    data class AccessTokenClaims(
        val userId: Long,
        val accountId: Long,
    )

    fun verify(token: String): AccessTokenClaims {
        val algorithm = Algorithm.HMAC256(accessTokenProperties.secretKey)
        val verifier = JWT.require(algorithm).build()
        val decodedToken = try {
            verifier.verify(token)
        } catch (e: TokenExpiredException) {
            throw AccessTokenException.TokenExpiredException()
        } catch (e: Exception) {
            throw AccessTokenException.InvalidTokenException()
        }

        return AccessTokenClaims(
            userId = decodedToken.getClaim("userId").asLong(),
            accountId = decodedToken.getClaim("accountId").asLong(),
        )
    }
}
