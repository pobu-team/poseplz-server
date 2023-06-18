package com.poseplz.server.domain.pose

data class PoseUpdateVo(
    val fileId: Long?,
    val tagIds: List<Long>,
)
