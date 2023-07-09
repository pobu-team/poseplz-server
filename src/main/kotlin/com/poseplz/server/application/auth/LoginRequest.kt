package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderType
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val providerType: ProviderType?,
    @field:NotBlank
    val providerUserId: String?,
)
