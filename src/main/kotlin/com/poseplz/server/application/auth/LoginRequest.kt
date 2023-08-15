package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class LoginRequest(
    @field:Schema(
        description = "인증제공자 종류",
        example = "KAKAO",
    )
    @field:NotNull
    val providerType: ProviderType?,
    @field:Schema(
        description = "OAuth 결과 값 (ex, AccessToken)",
        example = "kakaoAccessToken",
    )
    @field:NotBlank
    val providerUserCredential: String?,
)
