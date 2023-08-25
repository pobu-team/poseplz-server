package com.poseplz.server.application.file

import com.poseplz.server.domain.file.FileCreateVo
import com.poseplz.server.domain.file.FileNotFoundException
import com.poseplz.server.domain.file.FileService
import com.poseplz.server.domain.file.storage.StorageDownloadRequestVo
import com.poseplz.server.domain.file.storage.StorageService
import com.poseplz.server.domain.file.storage.StorageUploadRequestVo
import com.poseplz.server.ui.api.file.FileResponse
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.OutputStream

@Component
class FileApplicationService(
    private val fileService: FileService,
    private val storageService: StorageService,
) {
    fun download(
        fileId: Long,
        outputStream: OutputStream,
    ): FileDownloadResponseVo {
        val file = fileService.findById(fileId) ?: throw FileNotFoundException("File not found. fileId: $fileId")
        return FileDownloadResponseVo(
            name = file.name,
            contentType = file.contentType,
            size = file.size
        ) {
            storageService.download(
                it,
                StorageDownloadRequestVo(
                    fileId = file.fileId,
                    filename = file.name,
                ),
            )
        }
    }
    fun upload(
        inputStream: InputStream,
        fileUploadVo: FileUploadVo,
    ): FileResponse {
        return storageService.upload(
            inputStream = inputStream,
            storageUploadRequestVo = StorageUploadRequestVo(
                contentType = fileUploadVo.contentType,
                size = fileUploadVo.size,
            ),
        ).let {
            fileService.create(
                fileCreateVo = FileCreateVo(
                    url = it.url,
                    name = it.name,
                    contentType = it.contentType,
                    size = it.size,
                ),
            )
        }.toFileResponse()
    }
}
