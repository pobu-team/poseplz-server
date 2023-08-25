package com.poseplz.server.domain.file

data class FileCreateVo(
    val name: String,
    val url: String,
    val contentType: String,
    val size: Long,
    val width: Int? = null,
    val height: Int? = null,
)
