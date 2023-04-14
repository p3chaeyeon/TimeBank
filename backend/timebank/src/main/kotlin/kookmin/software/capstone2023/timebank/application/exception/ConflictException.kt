package kookmin.software.capstone2023.timebank.application.exception

class ConflictException(
    code: ApplicationErrorCode = ApplicationErrorCode.CONFLICT,
    override val message: String?,
) : ApplicationException(code, message)
