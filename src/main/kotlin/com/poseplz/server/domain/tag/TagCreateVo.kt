package com.poseplz.server.domain.tag

data class TagCreateVo(
    val tagType: TagType,
    val name: String,
    val selectorName: String,
    val selectorDisplayOrder: Int,
    val emojiImageUrl: String?,
    val emojiText: String?,
    val description: String?
)
