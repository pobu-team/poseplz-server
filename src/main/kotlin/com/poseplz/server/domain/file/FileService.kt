package com.poseplz.server.domain.file

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface FileService {
    fun create(fileCreateVo: FileCreateVo): File
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

    override fun findById(fileId: Long): File? {
        return fileRepository.findByIdOrNull(fileId)
    }
}
