package com.poseplz.server.ui.api.member

import com.poseplz.server.application.member.MemberApplicationService
import com.poseplz.server.ui.api.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberApplicationService: MemberApplicationService,
) {
    @GetMapping("/me")
    fun getMyInfo(
        @AuthenticationPrincipal memberId: Long,
    ): ApiResponse<MemberResponse> {
        return ApiResponse.success(
            data = MemberResponse.from(
                memberApplicationService.getMember(memberId),
            ),
        )
    }
}
