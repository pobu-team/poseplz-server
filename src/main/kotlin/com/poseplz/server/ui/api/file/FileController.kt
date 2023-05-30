package com.poseplz.server.ui.api.file

import com.poseplz.server.application.file.FileApplicationService
import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.ui.api.ApiResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.CacheControl
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.Duration

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
        response.addHeader("Cache-Control", CacheControl.maxAge(Duration.ofHours(24)).headerValue)
        fileDownloadResponseVo.write(response.outputStream)
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
