package com.poseplz.server.infrastructure.kakao

import com.poseplz.server.application.auth.ProviderUserNameService
import com.poseplz.server.domain.member.ProviderIdentifier
import com.poseplz.server.domain.member.ProviderType
import org.springframework.stereotype.Service

@Service
class KakaoUserNameService(
    private val kakaoAdminApiClient: KakaoAdminApiClient,
) : ProviderUserNameService {
    override fun getProviderUserName(providerIdentifier: ProviderIdentifier): String? {
        return kakaoAdminApiClient.getUserInfo(providerIdentifier.providerUserId)
            .kakaoAccount
            ?.profile
            ?.nickname
    }

    override fun supports(providerIdentifier: ProviderIdentifier) =
        providerIdentifier.providerType == ProviderType.KAKAO
}
