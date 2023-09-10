package com.poseplz.server.ui.api.file

import com.poseplz.server.domain.file.FileImageType

data class FileResponse(
    val fileId: String,
    val name: String,
    val contentType: String,
    val imageType: FileImageType,
    val url: String,
    val size: Long,
    val width: Int?,
    val height: Int?,
)
