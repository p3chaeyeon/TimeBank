package kookmin.software.capstone2023.timebank.presentation.api.auth.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.application.service.auth.token.AccessTokenService
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

/**
 * 사용자 인증을 처리하는 인터셉터입니다.
 */
@Component
class UserAuthenticationInterceptor(
    private val accessTokenService: AccessTokenService,
    private val userJpaRepository: UserJpaRepository,
    private val accountJpaRepository: AccountJpaRepository,
) : HandlerInterceptor {
    /**
     * 사용자 인증을 처리합니다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler HandlerMethod
     * @return Boolean
     * @throws UnauthorizedException
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return super.preHandle(request, response, handler)
        }

        if (request.getAttribute(RequestAttributes.USER_CONTEXT) != null) {
            return super.preHandle(request, response, handler)
        }

        val token = getAuthorizationToken(request)
            ?: return true

        val tokenClaims = accessTokenService.verify(token)

        val user = userJpaRepository.findByIdOrNull(tokenClaims.userId)
            ?: throw UnauthorizedException(message = null)

        val account = accountJpaRepository.findByIdOrNull(tokenClaims.accountId)
            ?: throw UnauthorizedException(message = null)

        if (user.accountId != account.id) {
            throw UnauthorizedException(message = null)
        }

        val userContext = UserContext(
            userId = user.id,
            accountId = account.id,
            accountType = account.type,
        )

        request.setAttribute(RequestAttributes.USER_CONTEXT, userContext)

        return true
    }

    /**
     * Authorization 헤더에서 토큰을 추출합니다.
     *
     * @param request HttpServletRequest
     * @return 토큰
     */
    private fun getAuthorizationToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)
        return if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            authorizationHeader.substring(BEARER_PREFIX.length)
        } else {
            null
        }
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
}