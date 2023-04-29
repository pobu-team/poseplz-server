package com.poseplz.server.ui.api.pose

import com.poseplz.server.ui.api.tag.TagResponse

data class PoseDetailResponse(
    val poseId: String,
    val thumbnailImageUrl: String,
    val imageUrl: String,
    val tags: List<TagResponse>,
)