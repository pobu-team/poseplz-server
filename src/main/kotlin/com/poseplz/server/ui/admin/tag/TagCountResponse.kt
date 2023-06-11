package com.poseplz.server.ui.admin.tag

import com.poseplz.server.domain.tag.Tag
import com.poseplz.server.domain.tag.TagType

data class TagCountResponse(
    val tagId: Long,
    var type: TagType,
    var name: String,
    var selectorName: String,
    var selectorDisplayOrder: Int,
    var emojiImageUrl: String? = null,
    var emojiText: String? = null,
    var description: String? = null,
    var deleted: Boolean = false,
    val count: Int,
) {
    companion object {
        fun of(tag: Tag, tagCount: Int) = TagCountResponse(
            tagId = tag.tagId,
            type = tag.type,
            name = tag.name,
            selectorName = tag.selectorName,
            selectorDisplayOrder = tag.selectorDisplayOrder,
            emojiImageUrl = tag.emojiImageUrl,
            emojiText = tag.emojiText,
            description = tag.description,
            deleted = tag.deleted,
            count = tagCount,
        )
    }
}
