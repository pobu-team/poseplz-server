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
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    )
    @field:NotBlank
    val providerUserCredential: String?,
)
