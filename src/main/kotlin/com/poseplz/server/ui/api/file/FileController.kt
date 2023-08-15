package com.poseplz.server.ui.api.file

import com.poseplz.server.application.file.FileApplicationService
import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.ui.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.CacheControl
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.Duration

@Tag(name = "파일", description = "파일 API")
@RestController
@RequestMapping("/api/v1/files")
class FileController(
    private val fileApplicationService: FileApplicationService,
) {
    @Operation(summary = "파일 단건 다운로드", description = "파일을 다운로드합니다.")
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

    @Operation(summary = "파일 단건 업로드", description = "파일을 업로드합니다.")
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
