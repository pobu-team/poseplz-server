package com.poseplz.server.ui.api.pose

import com.poseplz.server.application.pose.PoseApplicationService
import com.poseplz.server.domain.pose.PoseQueryRequestVo
import com.poseplz.server.ui.api.ApiResponse
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/poses")
class PoseController(
    private val poseApplicationService: PoseApplicationService,
) {
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
            ).content,
        )
    }

    @GetMapping("/{poseId}")
    fun getPose(
        @PathVariable poseId: Long,
    ): ApiResponse<PoseDetailResponse> {
        return ApiResponse.success(
            data = poseApplicationService.findByPoseId(poseId),
        )
    }

    @GetMapping("/count")
    fun count(): ApiResponse<PoseCountResponse> {
        return ApiResponse.success(
            data = PoseCountResponse(
                count = poseApplicationService.count(),
            ),
        )
    }

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
}
