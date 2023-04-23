package com.haeseong.pobuserver.ui.pose

import com.haeseong.pobuserver.domain.tag.TagType
import com.haeseong.pobuserver.ui.ApiResponse
import com.haeseong.pobuserver.ui.tag.TagResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/poses")
class PoseController {
    @GetMapping
    fun getPoses(
        @RequestParam(required = false) preparedPoseQuery: PreparedPoseQuery?,
        @RequestParam(required = false, defaultValue = "0") page: Int? = 0,
        @RequestParam(required = false, defaultValue = "20") size: Int? = 20,
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = listOf(),
        )
    }

    @GetMapping("/{poseId}")
    fun getPose(
        @PathVariable poseId: Long,
    ): ApiResponse<PoseDetailResponse> {
        return ApiResponse.success(
            data = PoseDetailResponse(
                poseId = "12345678901234567890",
                thumbnailImageUrl = "https://www.poseplz.com/images/2-1.png",
                imageUrl = "https://www.poseplz.com/images/2-1.png",
                tags = listOf(
                    TagResponse(
                        tagId = "2345678901",
                        name = "#2명",
                        selectorName = "2명",
                        selectorDisplayOrder = 2,
                        type = TagType.NUMBER_OF_PEOPLE,
                    ),
                    TagResponse(
                        tagId = "1",
                        name = "#심플",
                        emojiImageUrl = "https://www.poseplz.com/images/simple.png",
                        selectorName = "심플한",
                        selectorDisplayOrder = 1,
                        type = TagType.ATMOSPHERE_OF_POSE,
                    ),
                ),
            ),
        )
    }

    @PostMapping("/recommend")
    fun recommend(
        @RequestBody recommendRequest: PoseRecommendRequest,
    ): ApiResponse<List<PoseSimpleResponse>> {
        return ApiResponse.success(
            data = emptyList(),
        )
    }
}
