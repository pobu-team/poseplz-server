package com.poseplz.server.ui.api.pose

import com.poseplz.server.application.pose.PoseApplicationService
import com.poseplz.server.domain.pose.PoseCreateVo
import com.poseplz.server.domain.pose.PoseQueryRequestVo
import com.poseplz.server.domain.pose.PoseUpdateVo
import com.poseplz.server.infrastructure.springdoc.SpringdocConfig
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "포즈", description = "포즈 API")
@RestController
@RequestMapping("/api/v1/poses")
class PoseController(
    private val poseApplicationService: PoseApplicationService,
) {
    @Operation(summary = "포즈 목록 조회", description = "포즈 목록을 조회합니다.")
    @GetMapping
    fun getPoses(
        @RequestParam(required = false) preparedPoseQuery: PreparedPoseQuery?,
        @RequestParam(required = false, defaultValue = "") tagIds: Collection<Long>,
        @RequestParam(required = false, defaultValue = "0") page: Int = 0,
        @RequestParam(required = false, defaultValue = "20") size: Int = 20,
        @RequestParam(required = false) archived: Boolean? = null,
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = poseApplicationService.findByTagIds(
                poseQueryRequestVo = PoseQueryRequestVo(
                    tagIds = tagIds,
                    archived = archived,
                    pageable = PageRequest.of(page, size),
                ),
            ),
        )
    }

    @Operation(summary = "포즈 단건 조회", description = "포즈 1개를 조회합니다.")
    @GetMapping("/{poseId}")
    fun getPose(
        @PathVariable poseId: Long,
    ): ApiResponse<PoseDetailResponse> {
        return ApiResponse.success(
            data = poseApplicationService.findByPoseId(poseId),
        )
    }

    @Operation(summary = "전체 포즈 개수 조회", description = "전체 포즈의 개수를 조회합니다.")
    @GetMapping("/count")
    fun count(): ApiResponse<PoseCountResponse> {
        return ApiResponse.success(
            data = PoseCountResponse(
                count = poseApplicationService.count(),
            ),
        )
    }

    @Operation(summary = "포즈 추천", description = "요청한 추천 조건에 맞는 포즈 목록을 조회합니다.")
    @PostMapping("/recommend")
    fun recommend(
        @RequestBody recommendRequest: PoseRecommendRequest,
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = poseApplicationService.recommend(
                tagGroupIds = recommendRequest.tagGroupIds.map { it.toLong() },
                peopleCount = recommendRequest.peopleCount,
            ),
        )
    }

    @SecurityRequirement(name = SpringdocConfig.SECURITY_SCHEME_NAME)
    @Operation(summary = "포즈 추가", description = "사용자가 새 포즈를 추가합니다. ")
    @PostMapping
    fun create(
        @AuthenticationPrincipal memberId: Long,
        @RequestBody poseCreateRequest: PoseCreateRequest,
    ): ApiResponse<PoseDetailResponse> {
        return ApiResponse.success(
            data = poseApplicationService.createByMember(
                memberId = memberId,
                poseCreateVo = PoseCreateVo(
                    fileId = poseCreateRequest.fileId.toLong(),
                    tagIds = poseCreateRequest.tagIds.map { it.toLong() },
                    peopleCount = poseCreateRequest.peopleCount,
                ),
            ),
        )
    }

    @SecurityRequirement(name = SpringdocConfig.SECURITY_SCHEME_NAME)
    @Operation(summary = "포즈 수정", description = "사용자가 포즈를 수정합니다. ")
    @PutMapping("/{poseId}")
    fun update(
        @AuthenticationPrincipal memberId: Long,
        @PathVariable poseId: Long,
        @RequestBody poseUpdateRequest: PoseUpdateRequest,
    ): ApiResponse<PoseDetailResponse> {
        return ApiResponse.success(
            data = poseApplicationService.updateByMember(
                memberId, poseId, PoseUpdateVo(
                    fileId = poseUpdateRequest.fileId.toLong(),
                    tagIds = poseUpdateRequest.tagIds.map { it.toLong() },
                    peopleCount = poseUpdateRequest.peopleCount,
                )
            ),
        )
    }

    @SecurityRequirement(name = SpringdocConfig.SECURITY_SCHEME_NAME)
    @Operation(summary = "포즈 삭제", description = "사용자가 포즈를 삭제합니다. ")
    @DeleteMapping("/{poseId}")
    fun delete(
        @AuthenticationPrincipal memberId: Long,
        @PathVariable poseId: Long,
    ): ApiResponse<Unit> {
        poseApplicationService.delete(memberId, poseId)
        return ApiResponse.success()
    }
}
