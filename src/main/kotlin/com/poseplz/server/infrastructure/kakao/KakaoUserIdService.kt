package com.poseplz.server.infrastructure.kakao

import com.poseplz.server.application.auth.ProviderUserIdService
import com.poseplz.server.domain.member.ProviderIdentifier
import com.poseplz.server.domain.member.ProviderType
import org.springframework.stereotype.Service

@Service
class KakaoUserIdService(
    private val kakaoApiClient: KakaoApiClient,
) : ProviderUserIdService {
    override fun getProviderUserId(providerIdentifier: ProviderIdentifier): ProviderIdentifier {
        val kakaoUserId = kakaoApiClient.getKakaoUserId(providerIdentifier.providerUserId)
        return ProviderIdentifier(
            providerType = ProviderType.KAKAO,
            providerUserId = kakaoUserId,
        )
    }

    override fun supports(providerIdentifier: ProviderIdentifier) =
        providerIdentifier.providerType == ProviderType.KAKAO
}
