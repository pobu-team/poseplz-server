package com.poseplz.server.infrastructure.spring.security.preauth

import com.poseplz.server.application.auth.TokenService
import com.poseplz.server.infrastructure.spring.security.SecurityConfig
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class JwtAuthenticationProvider(
    private val tokenService: TokenService<Long>,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? {
        if (authentication !is PreAuthenticatedAuthenticationToken) {
            return null
        }

        val memberId = tokenService.decode(authentication.principal as String)
        return PreAuthenticatedAuthenticationToken(
            memberId,
            null,
            listOf(SimpleGrantedAuthority(SecurityConfig.MEMBER_ROLE_NAME)),
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return PreAuthenticatedAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
