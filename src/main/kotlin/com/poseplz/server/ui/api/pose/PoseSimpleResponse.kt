package com.poseplz.server.ui.api.pose

import com.poseplz.server.ui.api.file.FileResponse

data class PoseSimpleResponse(
    val poseId: String,
    val thumbnailImageUrl: String,
    val file: FileResponse,
    val poseCount: Int,
    val archived: Boolean,
)
