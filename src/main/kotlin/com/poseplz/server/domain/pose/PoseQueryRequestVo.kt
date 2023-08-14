package com.poseplz.server.domain.pose

import org.springframework.data.domain.Pageable

data class PoseQueryRequestVo(
    val tagIds: Collection<Long>,
    val archived: Boolean?,
    val pageable: Pageable,
)
