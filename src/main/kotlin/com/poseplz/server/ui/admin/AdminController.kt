package com.poseplz.server.ui.admin

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/")
@Controller
class AdminController {
    @GetMapping
    fun index(@AuthenticationPrincipal principal: OAuth2User): String {
        return "index"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}
