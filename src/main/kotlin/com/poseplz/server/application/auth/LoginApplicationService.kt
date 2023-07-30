package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.MemberService
import com.poseplz.server.domain.member.MemberVo
import com.poseplz.server.domain.member.ProviderIdentifier
import org.springframework.stereotype.Component

@Component
class LoginApplicationService(
    private val providerUserIdServices: List<ProviderUserIdService>,
    private val memberService: MemberService,
    private val tokenService: TokenService<Long>,
) {
    fun login(providerIdentifier: ProviderIdentifier): LoginResponseVo {
        val authenticatedProviderIdentifier = resolveLoginService(providerIdentifier).getProviderUserId(providerIdentifier)
        val member = (memberService.findByProviderIdentifier(authenticatedProviderIdentifier)
            ?: memberService.create(authenticatedProviderIdentifier))
        return LoginResponseVo(
            memberVo = MemberVo.from(member),
            accessToken = tokenService.encode(member.memberId),
        )
    }

    private fun resolveLoginService(providerIdentifier: ProviderIdentifier): ProviderUserIdService {
        return providerUserIdServices.firstOrNull { it.supports(providerIdentifier) }
            ?: throw IllegalArgumentException("지원하지 않는 로그인 서비스입니다. providerIdentifier: $providerIdentifier")
    }
}
