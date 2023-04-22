package com.haeseong.pobuserver.ui.pose

import com.haeseong.pobuserver.ui.tag.TagResponse

data class PoseDetailResponse(
    val poseId: String,
    val thumbnailImageUrl: String,
    val imageUrl: String,
    val tags: List<TagResponse>,
)
