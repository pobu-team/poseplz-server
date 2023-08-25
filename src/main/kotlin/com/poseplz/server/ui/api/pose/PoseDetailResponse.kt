package com.poseplz.server.ui.api.pose

import com.poseplz.server.ui.api.file.FileResponse
import com.poseplz.server.ui.api.tag.TagResponse

data class PoseDetailResponse(
    val poseId: String,
    val thumbnailImageUrl: String,
    val imageUrl: String,
    val file: FileResponse,
    val tags: List<TagResponse>,
    val peopleCount: Int,
    val archived: Boolean = false,
)
