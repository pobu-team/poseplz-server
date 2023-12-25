package com.poseplz.server.infrastructure.kakao

import com.poseplz.server.application.auth.LoginRequestVo
import com.poseplz.server.application.auth.ProviderUserProfileImageService
import com.poseplz.server.domain.member.ProviderType
import org.springframework.stereotype.Service

@Service
class KakaoUserProfileImageService(
    private val kakaoApiClient: KakaoApiClient,
) : ProviderUserProfileImageService {
    override fun getProviderUserProfileImage(loginRequestVo: LoginRequestVo): String? {
        return kakaoApiClient.getKakaoUserInfo(loginRequestVo.providerUserCredential!!)
            .kakaoAccount
            ?.profile
            ?.profileImageUrl
    }

    override fun supports(loginRequestVo: LoginRequestVo) =
        loginRequestVo.providerType == ProviderType.KAKAO
}
