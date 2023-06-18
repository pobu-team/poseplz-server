package com.poseplz.server.ui.admin.tag.group

data class TagGroupCreateRequest(
    val name: String,
    val peopleCounts: List<Int> = emptyList(),
    val tagIds: List<Long> = emptyList(),
)
