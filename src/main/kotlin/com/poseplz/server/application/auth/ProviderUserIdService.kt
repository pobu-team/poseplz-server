package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderIdentifier

interface ProviderUserIdService {
    fun getProviderUserId(providerIdentifier: ProviderIdentifier): ProviderIdentifier
    fun supports(providerIdentifier: ProviderIdentifier): Boolean
}
