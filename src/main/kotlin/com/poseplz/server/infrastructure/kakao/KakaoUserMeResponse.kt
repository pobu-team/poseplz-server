package com.poseplz.server.infrastructure.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class KakaoUserMeResponse (
    val id: Long,
    @JsonProperty("connected_at")
    val connectedAt: ZonedDateTime,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount? = null,
)

data class KakaoAccount (
    val profile: KakaoProfile,
)

data class KakaoProfile (
    @JsonProperty("nickname")
    val nickname: String?,
    @JsonProperty("thumbnail_image_url")
    val thumbnailImageUrl: String?,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String?,
)
