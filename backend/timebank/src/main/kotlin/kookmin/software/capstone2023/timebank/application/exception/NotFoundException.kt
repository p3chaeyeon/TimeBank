package kookmin.software.capstone2023.timebank.application.exception

class NotFoundException(
    code: ApplicationErrorCode = ApplicationErrorCode.NOT_FOUND,
    override val message: String?,
) : ApplicationException(code, message)