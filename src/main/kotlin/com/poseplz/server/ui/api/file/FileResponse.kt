package com.poseplz.server.ui.api.file

data class FileResponse(
    val fileId: String,
    val name: String,
    val contentType: String,
    val size: Long,
)
