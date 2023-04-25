package com.poseplz.server.infrastructure.spring

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser

class PoseplzAdminOidcUserService : OidcUserService() {
    override fun loadUser(userRequest: OidcUserRequest?): OidcUser {
        val loadedUser = super.loadUser(userRequest)
        val isInternalUser = loadedUser.attributes["email"]?.let { whitelists.contains(it) } ?: false
        if (!isInternalUser) {
            throw AccessDeniedException("접근 권한이 없습니다. email: ${loadedUser.attributes["email"]}")
        }
        return loadedUser
    }
    companion object {
        private val whitelists = listOf(
            "312lay@naver.com",
            "dkdud0312@gmail.com",
            "h96yesuny@gmail.com",
            "rkdalsgp1998@gmail.com",
            "seong0428@gmail.com",
        )
    }
}

