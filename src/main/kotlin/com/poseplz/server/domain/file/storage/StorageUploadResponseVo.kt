package com.poseplz.server.domain.file.storage

data class StorageUploadResponseVo(
    val url: String,
    val name: String,
    val contentType: String,
    val size: Long,
)
