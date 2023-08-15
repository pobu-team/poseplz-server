package com.poseplz.server.ui.api.tag

import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "태그 타입", description = "태그 타입 API")
@RestController
@RequestMapping("/api/v1/tag-types")
class TagTypeController {
    @Operation(summary = "태그 타입 목록 조회", description = "태그 타입 목록을 조회합니다.")
    @GetMapping
    fun getTagTypes(): ApiResponse<List<TagType>> {
        return ApiResponse.success(
            data = TagType.values().toList(),
        )
    }
}
