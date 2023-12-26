package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderIdentifier

interface ProviderUserNameService {
    fun getProviderUserName(providerIdentifier: ProviderIdentifier): String?
    fun supports(providerIdentifier: ProviderIdentifier): Boolean
}
