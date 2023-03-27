package kookmin.software.capstone2023.timebank.application.service.auth.token

import kookmin.software.capstone2023.timebank.application.exception.ApplicationErrorCode
import kookmin.software.capstone2023.timebank.application.exception.ApplicationException

sealed class AccessTokenException {
    class InvalidTokenException : ApplicationException(
        code = ApplicationErrorCode.UNAUTHORIZED,
        message = "유효하지 않은 토큰입니다.",
    )

    class TokenExpiredException : ApplicationException(
        code = ApplicationErrorCode.UNAUTHORIZED,
        message = "만료된 토큰입니다.",
    )
}
