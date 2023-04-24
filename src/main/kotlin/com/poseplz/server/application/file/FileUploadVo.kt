package com.poseplz.server.application.file

data class FileUploadVo(
    val name: String,
    val contentType: String,
    val size: Long,
)
