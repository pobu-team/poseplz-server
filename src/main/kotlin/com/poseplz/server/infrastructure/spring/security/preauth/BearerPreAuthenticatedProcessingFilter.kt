package com.poseplz.server.infrastructure.spring.security.preauth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter

class BearerPreAuthenticatedProcessingFilter : AbstractPreAuthenticatedProcessingFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any? {
        return request.getHeader("Authorization")
            ?.let { if (isBearer(it)) parseAccessToken(it) else null }
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any? {
        return null
    }

    private fun isBearer(authorizationHeader: String): Boolean {
        return authorizationHeader.startsWith(prefix = "bearer ", ignoreCase = true)
    }

    private fun parseAccessToken(authorizationHeader: String): String {
        return authorizationHeader.substring(7)
    }
}
