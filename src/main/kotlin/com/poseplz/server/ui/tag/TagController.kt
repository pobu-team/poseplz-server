package com.poseplz.server.ui.tag

import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/tags")
class TagController {
    @GetMapping
    fun getTags(
        @RequestParam(required = false) tagType: TagType?,
    ): ApiResponse<List<TagResponse>> {
        val tagResponses = listOf(
            TagResponse(
                tagId = "1234567890",
                name = "#1명",
                selectorName = "1명",
                selectorDisplayOrder = 1,
                type = TagType.NUMBER_OF_PEOPLE,
            ),
            TagResponse(
                tagId = "2345678901",
                name = "#2명",
                selectorName = "2명",
                selectorDisplayOrder = 2,
                type = TagType.NUMBER_OF_PEOPLE,
            ),
            TagResponse(
                tagId = "3456789012",
                name = "#3명",
                selectorName = "3명",
                selectorDisplayOrder = 3,
                type = TagType.NUMBER_OF_PEOPLE,
            ),
            TagResponse(
                tagId = "4567890123",
                name = "#4명",
                selectorName = "4명",
                selectorDisplayOrder = 4,
                type = TagType.NUMBER_OF_PEOPLE,
            ),
            TagResponse(
                tagId = "5678901234",
                name = "#5명",
                selectorName = "5명",
                selectorDisplayOrder = 5,
                type = TagType.NUMBER_OF_PEOPLE,
            ),
            TagResponse(
                tagId = "6789012345",
                name = "#6명",
                selectorName = "6명",
                selectorDisplayOrder = 6,
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
            TagResponse(
                tagId = "2",
                name = "#친근",
                emojiImageUrl = "https://www.poseplz.com/images/friendly.png",
                selectorName = "친근한",
                selectorDisplayOrder = 2,
                type = TagType.ATMOSPHERE_OF_POSE,
            ),
            TagResponse(
                tagId = "3",
                name = "#코믹",
                emojiImageUrl = "https://www.poseplz.com/images/fun.png",
                selectorName = "코믹한",
                selectorDisplayOrder = 3,
                type = TagType.ATMOSPHERE_OF_POSE,
            ),
            TagResponse(
                tagId = "4",
                name = "#로맨틱",
                emojiImageUrl = "https://www.poseplz.com/images/love.png",
                selectorName = "로맨틱한",
                selectorDisplayOrder = 4,
                type = TagType.ATMOSPHERE_OF_POSE,
            ),
            TagResponse(
                tagId = "11",
                name = "#생일 #기념일",
                emojiImageUrl = "https://www.poseplz.com/images/birthday.png",
                selectorName = "생일 / 기념일",
                selectorDisplayOrder = 1,
                type = TagType.NATURE_OF_GATHERING,
            ),
            TagResponse(
                tagId = "12",
                name = "#설날 #추석 #가족모임",
                emojiImageUrl = "https://www.poseplz.com/images/holiday.png",
                selectorName = "설날 / 추석 / 가족모임",
                selectorDisplayOrder = 2,
                type = TagType.NATURE_OF_GATHERING,
            ),
            TagResponse(
                tagId = "13",
                name = "#크리스마스 #연말",
                emojiImageUrl = "https://www.poseplz.com/images/christmas.png",
                selectorName = "크리스마스 / 연말",
                selectorDisplayOrder = 3,
                type = TagType.NATURE_OF_GATHERING,
            ),
        ).filter { tagType == null || it.type == tagType }
        return ApiResponse.success(
            data = tagResponses,
        )
    }
}
