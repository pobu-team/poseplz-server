package com.poseplz.server.ui.api.pose

data class PoseRecommendRequest(
    val tagGroupIds: List<String>,
    val peopleCount: Int,
)
