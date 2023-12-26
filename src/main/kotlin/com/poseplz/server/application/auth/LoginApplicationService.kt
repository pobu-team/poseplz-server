package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.MemberService
import com.poseplz.server.domain.member.MemberVo
import org.springframework.stereotype.Component

@Component
class LoginApplicationService(
    private val providerUserIdServices: List<ProviderUserIdService>,
    private val memberService: MemberService,
    private val tokenService: TokenService<Long>,
    private val providerUserNameService: ProviderUserNameService,
    private val providerUserProfileImageService: ProviderUserProfileImageService,
) {
    fun login(
        loginRequestVo: LoginRequestVo,
    ): LoginResponseVo {
        val authenticatedProviderIdentifier = resolveLoginService(loginRequestVo).getProviderUserId(loginRequestVo)
        val member = (memberService.findByProviderIdentifier(authenticatedProviderIdentifier)
            ?: memberService.create(authenticatedProviderIdentifier))
        if (member.name == null || member.profileImageUrl == null) {
            memberService.update(
                memberId = member.memberId,
                name = providerUserNameService.getProviderUserName(member.providerIdentifier),
                profileImageUrl = providerUserProfileImageService.getProviderUserProfileImage(member.providerIdentifier)
            )
        }
        return LoginResponseVo(
            memberVo = MemberVo.from(member),
            accessToken = tokenService.encode(member.memberId),
        )
    }

    private fun resolveLoginService(loginRequestVo: LoginRequestVo): ProviderUserIdService {
        return providerUserIdServices.firstOrNull { it.supports(loginRequestVo) }
            ?: throw IllegalArgumentException("지원하지 않는 로그인 서비스입니다. loginRequestVo: $loginRequestVo")
    }
}
