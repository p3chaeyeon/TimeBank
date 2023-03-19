package kookmin.software.capstone2023.timebank.application.exception

class BadRequestException(
    code: ApplicationErrorCode = ApplicationErrorCode.BAD_REQUEST,
    override val message: String?,
) : ApplicationException(code, message)