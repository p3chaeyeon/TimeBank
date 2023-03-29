package kookmin.software.capstone2023.timebank.application.configuration

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "application.authentication.test")
data class AuthenticationTestProperties(
    val enabled: Boolean? = false,
    val user: UserProperties? = null,
)