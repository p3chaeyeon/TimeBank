package kookmin.software.capstone2023.timebank.application.exception

open class ApplicationException(
    val code: ApplicationErrorCode,
    override val message: String?,
) : RuntimeException()