package com.poseplz.server.ui.api.tag

import com.poseplz.server.application.tag.TagApplicationService
import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@Tag(name = "태그", description = "태그 API")
@RestController
@RequestMapping("/api/v1/tags")
class TagController (
    private val tagApplicationService: TagApplicationService,
) {
    @Operation(summary = "태그 목록 조회", description = "태그 목록을 조회합니다.")
    @GetMapping
    fun getTags(
        @RequestParam(required = false) tagType: TagType?,
    ): ApiResponse<List<TagResponse>> {
        return ApiResponse.success(
            data = tagApplicationService.findByTagType(tagType, Pageable.unpaged()).content,
        )
    }

    @Operation(summary = "태그 단건 조회", description = "태그 1개를 조회합니다.")
    @GetMapping("/{tagId}")
    fun getTag(
        @PathVariable tagId: Long,
    ): ApiResponse<TagDetailResponse> {
        return ApiResponse.success(
            data = tagApplicationService.getTagByTagId(tagId),
        )
    }
}
