package com.poseplz.server.ui.api.tag.group

import com.poseplz.server.ui.api.tag.TagResponse

data class TagGroupDetailResponse(
    val tagGroupId: String,
    val name: String,
    val peopleCounts: List<Int>,
    val tags: List<TagResponse>,
)
