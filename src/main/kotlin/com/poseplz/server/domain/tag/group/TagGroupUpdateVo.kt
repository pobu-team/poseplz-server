package com.poseplz.server.domain.tag.group

data class TagGroupUpdateVo(
    val name: String,
    val peopleCounts: List<Int>,
    val tagIds: List<Long>,
)
