package com.poseplz.server.ui.api.pose

data class PoseSimpleResponse(
    val poseId: String,
    val thumbnailImageUrl: String,
    val poseCount: Int,
)
