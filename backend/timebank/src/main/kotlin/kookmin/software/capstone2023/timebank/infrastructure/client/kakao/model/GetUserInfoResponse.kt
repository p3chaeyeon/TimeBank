package kookmin.software.capstone2023.timebank.infrastructure.client.kakao.model

import java.time.LocalDateTime

data class GetUserInfoResponse(
    /**
     * 회원번호
     */
    val id: Long,

    /**
     * 카카오계정 정보
     */
    val kakaoAccount: KakaoAccount?,

    /**
     * 서비스 연결일시 (UTC)
     */
    val connectedAt: LocalDateTime?,
)
