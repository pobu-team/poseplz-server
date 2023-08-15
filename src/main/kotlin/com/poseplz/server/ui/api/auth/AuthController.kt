package com.poseplz.server.ui.api.auth

import com.poseplz.server.application.auth.LoginApplicationService
import com.poseplz.server.application.auth.LoginRequest
import com.poseplz.server.application.auth.LoginRequestVo
import com.poseplz.server.ui.api.ApiResponse
import com.poseplz.server.ui.api.member.MemberResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증", description = "인증 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val loginApplicationService: LoginApplicationService,
) {
    @Operation(
        summary = "로그인",
        description = "OAuth 마치고 인증 결과를 통해 회원을 식별합니다. 회원이 아니면 새로 가입합니다.",
    )
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
    ): ApiResponse<LoginResponse> {
        val loginResponseVo = loginApplicationService.login(
            LoginRequestVo(
                providerType = loginRequest.providerType!!,
                providerUserCredential = loginRequest.providerUserCredential,
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
