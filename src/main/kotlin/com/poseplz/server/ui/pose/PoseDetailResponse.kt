package com.poseplz.server.ui.pose

import com.poseplz.server.ui.tag.TagResponse

data class PoseDetailResponse(
    val poseId: String,
    val thumbnailImageUrl: String,
    val imageUrl: String,
    val tags: List<TagResponse>,
)
