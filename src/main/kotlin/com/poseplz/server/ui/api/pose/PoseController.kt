package com.poseplz.server.ui.api.pose

import com.poseplz.server.application.pose.PoseApplicationService
import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.ApiResponse
import com.poseplz.server.ui.api.tag.TagResponse
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
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = poseApplicationService.findByTagIds(tagIds, PageRequest.of(page, size)).content,
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

    @PostMapping("/recommend")
    fun recommend(
        @RequestBody recommendRequest: PoseRecommendRequest,
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = poseApplicationService.recommend(
                tagGroupIds = recommendRequest.tagGroupIds.map { it.toLong() },
            ),
        )
    }
}
