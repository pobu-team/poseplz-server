package com.poseplz.server.ui.api.tag

import com.poseplz.server.domain.tag.TagType
import com.poseplz.server.ui.api.tag.group.TagGroupResponse

data class TagDetailResponse(
    val tagId: String,
    val tagGroups: List<TagGroupResponse>,
    val type: TagType,
    val name: String,
    val selectorName: String,
    val selectorDisplayOrder: Int,
    val emojiImageUrl: String? = null,
    val emojiText: String? = null,
    val description: String? = null,
)
