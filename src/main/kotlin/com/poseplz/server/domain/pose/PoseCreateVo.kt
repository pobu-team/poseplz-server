package com.poseplz.server.domain.pose

data class PoseCreateVo(
    val fileId: Long,
    val tagIds: List<Long>,
)
