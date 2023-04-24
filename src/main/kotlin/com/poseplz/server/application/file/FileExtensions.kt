package com.poseplz.server.application.file

import com.poseplz.server.domain.file.File
import com.poseplz.server.ui.file.FileResponse

fun File.toFileResponse(): FileResponse {
    return FileResponse(
        fileId = this.fileId.toString(),
        name = this.name,
        contentType = this.contentType,
        size = this.size,
    )
}
