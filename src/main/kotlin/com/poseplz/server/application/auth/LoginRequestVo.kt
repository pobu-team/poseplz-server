package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderType

data class LoginRequestVo(
    val providerType: ProviderType,
    val providerUserCredential: String? = null,
)
