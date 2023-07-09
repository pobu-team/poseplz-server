package com.poseplz.server.ui.api.auth

import com.poseplz.server.application.auth.LoginApplicationService
import com.poseplz.server.application.auth.LoginRequest
import com.poseplz.server.domain.member.ProviderIdentifier
import com.poseplz.server.ui.api.ApiResponse
import com.poseplz.server.ui.api.member.MemberResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val loginApplicationService: LoginApplicationService,
) {
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
    ): ApiResponse<LoginResponse> {
        val loginResponseVo = loginApplicationService.login(
            ProviderIdentifier(
                providerType = loginRequest.providerType!!,
                providerUserId = loginRequest.providerUserId!!,
            ),
        )
        return ApiResponse.success(
            LoginResponse(
                member = MemberResponse.from(loginResponseVo.memberVo),
                accessToken = loginResponseVo.accessToken,
            ),
        )
    }
}
