package com.poseplz.server.infrastructure.kakao

import com.poseplz.server.application.auth.LoginRequestVo
import com.poseplz.server.application.auth.ProviderUserNameService
import com.poseplz.server.domain.member.ProviderType
import org.springframework.stereotype.Service

@Service
class KakaoUserNameService(
    private val kakaoApiClient: KakaoApiClient,
) : ProviderUserNameService {
    override fun getProviderUserName(loginRequestVo: LoginRequestVo): String? {
        return kakaoApiClient.getKakaoUserInfo(loginRequestVo.providerUserCredential!!)
            .kakaoAccount
            ?.profile
            ?.nickName
    }

    override fun supports(loginRequestVo: LoginRequestVo) =
        loginRequestVo.providerType == ProviderType.KAKAO
}
