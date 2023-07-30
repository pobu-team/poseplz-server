package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class LoginRequest(
    @field:NotNull
    val providerType: ProviderType?,
    @field:NotBlank
    val providerUserId: String?,
)
