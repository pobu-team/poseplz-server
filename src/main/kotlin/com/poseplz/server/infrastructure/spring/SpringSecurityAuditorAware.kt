package com.poseplz.server.infrastructure.spring

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import java.util.*

class SpringSecurityAuditorAware : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null || !authentication.isAuthenticated) {
            return Optional.of("system")
        }
        return Optional.ofNullable(
            (authentication.principal as DefaultOidcUser).attributes["email"].toString()
        )
    }
}
