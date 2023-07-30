package com.poseplz.server.infrastructure.spring.security.preauth

import com.poseplz.server.infrastructure.spring.security.SecurityConfig
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class JwtAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? {
        if (authentication !is PreAuthenticatedAuthenticationToken) {
            return null
        }
        return PreAuthenticatedAuthenticationToken(
            1L,
            null,
            listOf(SimpleGrantedAuthority(SecurityConfig.MEMBER_ROLE_NAME)),
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return PreAuthenticatedAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
