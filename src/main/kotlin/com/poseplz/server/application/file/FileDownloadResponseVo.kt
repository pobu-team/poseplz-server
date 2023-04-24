package com.poseplz.server.application.file

data class FileDownloadResponseVo(
    val name: String,
    val contentType: String,
    val size: Long,
)
