package com.poseplz.server.ui.api.pose

import com.poseplz.server.application.pose.PoseArchiveApplicationService
import com.poseplz.server.domain.pose.PoseQueryRequestVo
import com.poseplz.server.infrastructure.springdoc.SpringdocConfig
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SecurityRequirement(name = SpringdocConfig.SECURITY_SCHEME_NAME)
@Tag(name = "찜한 포즈", description = "찜한 포즈 API")
@RestController
@RequestMapping("/api/v1/archived-poses")
class PoseArchiveController(
    private val poseArchiveApplicationService: PoseArchiveApplicationService,
) {
    @Operation(summary = "찜한 포즈 목록 조회", description = "찜한 포즈의 목록을 조회합니다. 찜한 포즈는 최신순으로 정렬됩니다.")
    @GetMapping
    fun getArchivedPoses(
        @AuthenticationPrincipal memberId: Long,
        @RequestParam(required = false, defaultValue = "") tagIds: Collection<Long>,
        @RequestParam(required = false, defaultValue = "0") page: Int = 0,
        @RequestParam(required = false, defaultValue = "20") size: Int = 20,
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = poseArchiveApplicationService.getArchivedPoses(
                memberId = memberId,
                poseQueryRequestVo = PoseQueryRequestVo(
                    tagIds = tagIds,
                    archived = true,
                    pageable = PageRequest.of(page, size),
                ),
            ).content
        )
    }

    @Operation(summary = "찜한 포즈 추가", description = "찜한 포즈를 새로 추가합니다. 이미 추가된 포즈를 다시 추가하더라도 1개만 추가되고, 성공으로 응답합니다.")
    @PostMapping("/{poseId}")
    fun archivePose(
        @AuthenticationPrincipal memberId: Long,
        @PathVariable poseId: Long,
    ): ApiResponse<PoseSimpleResponse> {
        return ApiResponse.success(
            data = poseArchiveApplicationService.archivePose(memberId, poseId)
        )
    }

    @Operation(summary = "찜한 포즈 삭제", description = "찜한 포즈를 삭제합니다. 이미 삭제된 포즈를 다시 삭제 요청해도 1개만 삭제처리하고 성공으로 응답합니다.")
    @DeleteMapping("/{poseId}")
    fun unarchivePose(
        @AuthenticationPrincipal memberId: Long,
        @PathVariable poseId: Long,
    ): ApiResponse<Unit> {
        poseArchiveApplicationService.unarchivePose(memberId, poseId)
        return ApiResponse.success()
    }

    @Operation(summary = "찜한 포즈 모두 삭제", description = "찜한 포즈를 모두 삭제합니다. 삭제할 포즈가 없는 경우에도 성공으로 응답합니다.")
    @DeleteMapping
    fun unarchiveAllPoses(
        @AuthenticationPrincipal memberId: Long,
    ): ApiResponse<Unit> {
        poseArchiveApplicationService.unarchiveAllPoses(memberId)
        return ApiResponse.success()
    }
}
