package kookmin.software.capstone2023.timebank.application.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.access-token")
data class AccessTokenProperties(
    val secretKey: String,
)
