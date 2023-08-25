package com.poseplz.server.domain.file.event

import com.poseplz.server.domain.file.File

data class FileCreatedEvent(
    val fileId: Long,
) {
    companion object {
        fun from(file: File): FileCreatedEvent {
            return FileCreatedEvent(
                fileId = file.fileId,
            )
        }
    }

}
