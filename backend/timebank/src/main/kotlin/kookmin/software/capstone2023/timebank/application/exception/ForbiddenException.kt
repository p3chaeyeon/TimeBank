package kookmin.software.capstone2023.timebank.application.exception

class ForbiddenException(
    code: ApplicationErrorCode = ApplicationErrorCode.FORBIDDEN,
    override val message: String?,
) : ApplicationException(code, message)