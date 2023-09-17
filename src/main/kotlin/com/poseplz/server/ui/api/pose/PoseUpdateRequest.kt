package com.poseplz.server.ui.api.pose

data class PoseUpdateRequest(
    val peopleCount: Int,
    val tagIds: List<String>,
    val fileId: String,
)
