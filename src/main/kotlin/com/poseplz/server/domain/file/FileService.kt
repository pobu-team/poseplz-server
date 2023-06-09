package com.poseplz.server.domain.file

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface FileService {
    fun create(fileCreateVo: FileCreateVo): File
    fun delete(fileId: Long)
    fun findAll(pageable: Pageable): Page<File>
    fun findById(fileId: Long): File?
}

@Service
@Transactional(readOnly = true)
class FileServiceImpl(
    private val fileRepository: FileRepository,
) : FileService {
    @Transactional
    override fun create(fileCreateVo: FileCreateVo): File {
        File(
            url = fileCreateVo.url,
            name = fileCreateVo.name,
            contentType = fileCreateVo.contentType,
            size = fileCreateVo.size,
        ).let {
            return fileRepository.save(it)
        }
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
