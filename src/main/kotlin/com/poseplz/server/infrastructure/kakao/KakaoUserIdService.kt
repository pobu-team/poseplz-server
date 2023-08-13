package com.poseplz.server.infrastructure.kakao

import com.poseplz.server.application.auth.LoginRequestVo
import com.poseplz.server.application.auth.ProviderUserIdService
import com.poseplz.server.domain.member.ProviderIdentifier
import com.poseplz.server.domain.member.ProviderType
import org.springframework.stereotype.Service

@Service
class KakaoUserIdService(
    private val kakaoApiClient: KakaoApiClient,
) : ProviderUserIdService {
    override fun getProviderUserId(loginRequestVo: LoginRequestVo): ProviderIdentifier {
        val kakaoUserId = kakaoApiClient.getKakaoUserId(loginRequestVo.providerUserCredential!!)
        return ProviderIdentifier(
            providerType = ProviderType.KAKAO,
            providerUserId = kakaoUserId,
        )
    }

    override fun supports(loginRequestVo: LoginRequestVo) =
        loginRequestVo.providerType == ProviderType.KAKAO
}
