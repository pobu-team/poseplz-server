package com.poseplz.server.ui.api.file

data class FileResponse(
    val fileId: String,
    val name: String,
    val contentType: String,
    val url: String,
    val size: Long,
    val width: Int?,
    val height: Int?,
)
