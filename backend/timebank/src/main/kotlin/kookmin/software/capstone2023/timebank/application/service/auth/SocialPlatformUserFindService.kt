package kookmin.software.capstone2023.timebank.application.service.auth

import kookmin.software.capstone2023.timebank.domain.model.auth.SocialPlatformType
import kookmin.software.capstone2023.timebank.infrastructure.client.kakao.KakaoRestClient
import org.springframework.stereotype.Service

@Service
class SocialPlatformUserFindService(
    private val kakaoRestClient: KakaoRestClient,
) {
    data class SocialUser(
        val type: SocialPlatformType,
        val id: String,
    )

    fun getUser(type: SocialPlatformType, accessToken: String): SocialUser {
        when (type) {
            SocialPlatformType.KAKAO -> {
                val kakaoUser = kakaoRestClient.getUserInfo(
                    authorization = "Bearer $accessToken",
                )

                return SocialUser(
                    type = SocialPlatformType.KAKAO,
                    id = kakaoUser.id.toString(),
                )
            }
        }
    }
}
