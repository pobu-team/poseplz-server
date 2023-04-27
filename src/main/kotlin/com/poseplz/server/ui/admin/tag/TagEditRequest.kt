package com.poseplz.server.ui.admin.tag

import com.poseplz.server.domain.tag.TagType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TagEditRequest(
    @field:NotNull
    val tagType: TagType?,
    @field:NotBlank
    val name: String?,
    @field:NotBlank
    val selectorName: String?,
    val selectorDisplayOrder: Int?,
    val emojiImageUrl: String?,
    val emojiText: String?,
    val description: String?
)
