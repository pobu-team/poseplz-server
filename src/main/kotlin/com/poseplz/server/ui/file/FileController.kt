package com.poseplz.server.ui.file

import com.poseplz.server.application.file.FileApplicationService
import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.ui.ApiResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/files")
class FileController(
    private val fileApplicationService: FileApplicationService,
) {
    @GetMapping("/{fileId}")
    fun getFile(
        @PathVariable fileId: Long,
        response: HttpServletResponse,
    ) {
        val fileDownloadResponseVo = fileApplicationService.download(
            fileId = fileId,
            outputStream = response.outputStream,
        )
        response.contentType = fileDownloadResponseVo.contentType
        response.setContentLengthLong(fileDownloadResponseVo.size)
    }

    @PostMapping(consumes = ["multipart/form-data"])
    fun upload(
        @RequestPart file: MultipartFile,
    ): ApiResponse<FileResponse> {
        return ApiResponse.success(
            data = fileApplicationService.upload(
                inputStream = file.inputStream,
                fileUploadVo = FileUploadVo(
                    name = file.originalFilename ?: "",
                    contentType = file.contentType ?: "",
                    size = file.size,
                )
            )
        )
    }
}
