package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.ProviderIdentifier

interface ProviderUserProfileImageService {
    fun getProviderUserProfileImage(providerIdentifier: ProviderIdentifier): String?
    fun supports(providerIdentifier: ProviderIdentifier): Boolean
}
