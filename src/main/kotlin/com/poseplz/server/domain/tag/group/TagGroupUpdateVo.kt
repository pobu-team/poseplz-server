package com.poseplz.server.domain.tag.group

data class TagGroupUpdateVo(
    val name: String,
    val tagIds: List<Long>,
)
