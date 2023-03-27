package kookmin.software.capstone2023.timebank.application.service.auth.model

data class PasswordAuthenticationRequest(
    val username: String,
    val password: String,
)