package com.poseplz.server.ui.api.member

import com.poseplz.server.application.pose.PoseApplicationService
import com.poseplz.server.infrastructure.springdoc.SpringdocConfig
import com.poseplz.server.ui.api.ApiResponse
import com.poseplz.server.ui.api.pose.PoseSimpleResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SecurityRequirement(name = SpringdocConfig.SECURITY_SCHEME_NAME)
@Tag(name = "포즈", description = "포즈 API")
@RestController
@RequestMapping("/api/v1/members/me/poses")
class MemberPoseController(
    private val poseApplicationService: PoseApplicationService,
) {
    @GetMapping
    fun getMyPoses(
        @AuthenticationPrincipal memberId: Long,
        @PageableDefault pageable: Pageable
    ): ApiResponse<List<PoseSimpleResponse>> {
        val poseSimpleResponses = poseApplicationService.findByPoses(memberId, pageable)
        return ApiResponse.success(
            data = poseSimpleResponses.content,
        )
    }
}
