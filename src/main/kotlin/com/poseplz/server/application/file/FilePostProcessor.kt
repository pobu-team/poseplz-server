package com.poseplz.server.application.file

import com.poseplz.server.domain.file.FileService
import com.poseplz.server.domain.file.FileUpdateVo
import com.poseplz.server.domain.file.event.FileCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import java.net.URL
import javax.imageio.ImageIO

@Component
class FilePostProcessor(
    private val fileService: FileService,
) {
    @Async
    @TransactionalEventListener(FileCreatedEvent::class)
    fun updateMetadata(fileCreatedEvent: FileCreatedEvent) {
        log.info("updateMetadata: $fileCreatedEvent")
        val file = fileService.findById(fileCreatedEvent.fileId) ?: return
        val image = ImageIO.read(URL(file.url))
        fileService.update(
            fileId = file.fileId,
            fileUpdateVo = FileUpdateVo(
                width = image.width,
                height = image.height,
            )
        )
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(FilePostProcessor::class.java)
    }
}
