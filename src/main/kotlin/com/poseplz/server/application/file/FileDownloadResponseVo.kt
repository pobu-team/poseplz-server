package com.poseplz.server.application.file

import java.io.OutputStream

data class FileDownloadResponseVo(
    val name: String,
    val contentType: String,
    val size: Long,
    val write: (OutputStream) -> Unit
)
