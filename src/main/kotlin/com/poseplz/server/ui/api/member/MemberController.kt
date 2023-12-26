package com.poseplz.server.ui.api.member

import com.poseplz.server.application.member.MemberApplicationService
import com.poseplz.server.infrastructure.springdoc.SpringdocConfig
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SecurityRequirement(name = SpringdocConfig.SECURITY_SCHEME_NAME)
@Tag(name = "회원", description = "회원 API")
@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberApplicationService: MemberApplicationService,
) {
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
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

    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @GetMapping("/{memberId:\\d+}")
    fun getMemberInfo(
        @PathVariable memberId: Long,
    ): ApiResponse<MemberResponse> {
        return ApiResponse.success(
            data = MemberResponse.from(
                memberApplicationService.getMember(memberId),
            ),
        )
    }
}
