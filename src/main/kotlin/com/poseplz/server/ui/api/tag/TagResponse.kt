package com.poseplz.server.ui.api.tag

import com.poseplz.server.domain.tag.TagType

data class TagResponse(
    val tagId: String,
    val type: TagType,
    val name: String,
    val selectorName: String,
    val selectorDisplayOrder: Int,
    val emojiImageUrl: String? = null,
    val emojiText: String? = null,
    val description: String? = null,
)
