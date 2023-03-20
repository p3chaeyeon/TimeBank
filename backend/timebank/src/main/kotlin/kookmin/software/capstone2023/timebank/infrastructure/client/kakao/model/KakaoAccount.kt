package kookmin.software.capstone2023.timebank.infrastructure.client.kakao.model

data class KakaoAccount(
    val profileNicknameNeedsAgreement: Boolean?,
    val profileImageNeedsAgreement: Boolean?,
    val profile: KakaoAccountProfile?,
)
