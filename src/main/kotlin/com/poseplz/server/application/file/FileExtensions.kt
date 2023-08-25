package com.poseplz.server.application.file

import com.poseplz.server.domain.file.File
import com.poseplz.server.ui.api.file.FileResponse

fun File.toFileResponse(): FileResponse {
    return FileResponse(
        fileId = this.fileId.toString(),
        name = this.name,
        contentType = this.contentType,
        url = "/api/v1/files/${this.fileId}",
        size = this.size,
        width = this.width,
        height = this.height,
    )
}
