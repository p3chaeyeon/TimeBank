package kookmin.software.capstone2023.timebank.application.service.auth.model

import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType

data class SocialAuthenticationRequest(
    val socialPlatformType: SocialPlatformType,
    val accessToken: String,
)
