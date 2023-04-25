package com.poseplz.server.ui.api.tag

import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tag-types")
class TagTypeController {
    @GetMapping
    fun getTagTypes(): ApiResponse<List<TagType>> {
        return ApiResponse.success(
            data = TagType.values().toList(),
        )
    }
}
