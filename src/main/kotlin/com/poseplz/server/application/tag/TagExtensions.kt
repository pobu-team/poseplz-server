package com.poseplz.server.application.tag

import com.poseplz.server.domain.tag.Tag
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
