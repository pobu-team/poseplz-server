package com.poseplz.server.ui.api.tag

import com.poseplz.server.domain.tag.TagType

data class TagResponse(
    val tagId: String,
    val name: String,
    val emojiImageUrl: String? = null,
    val selectorName: String,
    val selectorDisplayOrder: Int,
    val description: String? = null,
    val type: TagType,
)
