package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderIdentifier

interface ProviderUserIdService {
    fun getProviderUserId(loginRequestVo: LoginRequestVo): ProviderIdentifier
    fun supports(loginRequestVo: LoginRequestVo): Boolean
}
