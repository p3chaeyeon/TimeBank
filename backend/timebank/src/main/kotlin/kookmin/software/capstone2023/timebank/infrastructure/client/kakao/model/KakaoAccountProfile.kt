package kookmin.software.capstone2023.timebank.infrastructure.client.kakao.model

data class KakaoAccountProfile(
    /**
     * 닉네임
     *
     * 필요한 동의 항목: 프로필 정보(닉네임/프로필 사진) 또는 닉네임
     */
    val nickname: String?,

    /**
     * 프로필 이미지 URL
     * 640px * 640px 또는 480px * 480px
     *
     * 필요한 동의 항목: 프로필 정보(닉네임/프로필 사진) 또는 프로필 사진
     *
     */
    val profileImageUrl: String?,

    /**
     * 프로필 미리보기 이미지 URL
     * 110px * 110px 또는 100px * 100px
     *
     * 필요한 동의 항목: 프로필 정보(닉네임/프로필 사진) 또는 프로필 사진
     */
    val thumbnailImageUrl: String?,

    /**
     * 프로필 사진 URL이 기본 프로필 사진 URL인지 여부
     *
     * 사용자가 등록한 프로필 사진이 없을 경우, 기본 프로필 사진 제공
     * true: 기본 프로필 사진
     * false: 사용자가 등록한 프로필 사진
     *
     * 필요한 동의 항목: 프로필 정보(닉네임/프로필 사진) 또는 프로필 사진
     */
    val isDefaultImage: Boolean?,
)
