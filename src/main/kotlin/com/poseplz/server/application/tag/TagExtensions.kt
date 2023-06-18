package com.poseplz.server.application.tag

import com.poseplz.server.application.tag.group.toTagGroupResponse
import com.poseplz.server.domain.tag.Tag
import com.poseplz.server.ui.api.tag.TagDetailResponse
import com.poseplz.server.ui.api.tag.TagResponse

fun Tag.toTagResponse(): TagResponse {
    return TagResponse(
        tagId = this.tagId.toString(),
        type = this.type,
        name = this.name,
        selectorName = this.selectorName,
        selectorDisplayOrder = this.selectorDisplayOrder,
        emojiImageUrl = this.emojiImageUrl,
        emojiText = this.emojiText,
        description = this.description,
    )
}

fun Tag.toTagDetailResponse(): TagDetailResponse {
    return TagDetailResponse(
        tagId = this.tagId.toString(),
        tagGroups = this.tagGroupTags.map { it.tagGroup.toTagGroupResponse() },
        type = this.type,
        name = this.name,
        selectorName = this.selectorName,
        selectorDisplayOrder = this.selectorDisplayOrder,
        emojiImageUrl = this.emojiImageUrl,
        emojiText = this.emojiText,
        description = this.description,
    )
}
