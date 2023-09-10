package com.poseplz.server.domain.file

import com.poseplz.server.domain.file.event.FileCreatedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface FileService {
    fun create(fileCreateVo: FileCreateVo): File
    fun update(fileId: Long, fileUpdateVo: FileUpdateVo): File
    fun delete(fileId: Long)
    fun findAll(pageable: Pageable): Page<File>
    fun findById(fileId: Long): File?
}

@Service
@Transactional(readOnly = true)
class FileServiceImpl(
    private val fileRepository: FileRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : FileService {
    @Transactional
    override fun create(fileCreateVo: FileCreateVo): File {
        val file = File.from(fileCreateVo)
        fileRepository.save(file)
        applicationEventPublisher.publishEvent(FileCreatedEvent.from(file))
        return file
    }

    @Transactional
    override fun update(fileId: Long, fileUpdateVo: FileUpdateVo): File {
        val file = fileRepository.findByIdOrNull(fileId) ?: throw FileNotFoundException()
        file.width = fileUpdateVo.width
        file.height = fileUpdateVo.height
        return file
    }

    override fun delete(fileId: Long) {
        fileRepository.findByIdOrNull(fileId)?.delete()
    }

    override fun findAll(pageable: Pageable): Page<File> {
        return fileRepository.findAll(pageable)
    }

    override fun findById(fileId: Long): File? {
        return fileRepository.findByIdOrNull(fileId)
    }
}
