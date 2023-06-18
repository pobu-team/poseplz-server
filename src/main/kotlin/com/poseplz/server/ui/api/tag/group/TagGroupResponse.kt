package com.poseplz.server.ui.api.tag.group

data class TagGroupResponse(
    val tagGroupId: String,
    val name: String,
    val peopleCounts: List<Int>,
)
