package com.poseplz.server.ui.api

import com.poseplz.server.domain.file.FileRepository
import com.poseplz.server.domain.file.FileService
import com.poseplz.server.infrastructure.aws.AmazonS3Service
import com.poseplz.server.ui.MigrationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.*
import javax.imageio.ImageIO

@RestController
@RequestMapping ("/api/v1/files/migrate")
class ImageMigrationController(
    private val fileService: FileService,
    private val storageService: AmazonS3Service,
    private val migrationService: MigrationService,
) {
    @PostMapping
    fun migrate(): ApiResponse<Unit> {
        val files = fileService.findAll(Pageable.unpaged()).content
        files.forEach {
            try {
                val inputStream = storageService.download(it.name)
                val image = ImageIO.read(inputStream)
                val width = image.width
                val height = image.height
                migrationService.migrate(it.fileId, width, height)
            } catch (e: Exception) {
                log.error("fileId: ${it.fileId}, name: ${it.name}", e)
            }
        }
        return ApiResponse.success(Unit)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ImageMigrationController::class.java)
    }
}
