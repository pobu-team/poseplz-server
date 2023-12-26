package com.poseplz.server.infrastructure.kakao

import com.poseplz.server.application.auth.ProviderUserProfileImageService
import com.poseplz.server.domain.member.ProviderIdentifier
import com.poseplz.server.domain.member.ProviderType
import org.springframework.stereotype.Service

@Service
class KakaoUserProfileImageService(
    private val kakaoAdminApiClient: KakaoAdminApiClient,
) : ProviderUserProfileImageService {
    override fun getProviderUserProfileImage(providerIdentifier: ProviderIdentifier): String? {
        return kakaoAdminApiClient.getUserInfo(providerIdentifier.providerUserId)
            .kakaoAccount
            ?.profile
            ?.profileImageUrl
    }

    override fun supports(providerIdentifier: ProviderIdentifier) =
        providerIdentifier.providerType == ProviderType.KAKAO
}
