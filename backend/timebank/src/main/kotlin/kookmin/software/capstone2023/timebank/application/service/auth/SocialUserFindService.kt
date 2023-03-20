package kookmin.software.capstone2023.timebank.application.service.auth

import kookmin.software.capstone2023.timebank.domain.model.SocialPlatformType
import kookmin.software.capstone2023.timebank.infrastructure.client.kakao.KakaoRestClient
import org.springframework.stereotype.Service

@Service
class SocialUserFindService(
    private val kakaoRestClient: KakaoRestClient,
) {
    data class SocialUser(
        val id: String,
    )

    fun getSocialUser(platformType: SocialPlatformType, accessToken: String): SocialUser {
        when (platformType) {
            SocialPlatformType.KAKAO -> {
                val kakaoUser = kakaoRestClient.getUserInfo(
                    authorization = "Bearer $accessToken"
                )

                return SocialUser(
                    id = kakaoUser.id.toString(),
                )
            }
        }
    }
}