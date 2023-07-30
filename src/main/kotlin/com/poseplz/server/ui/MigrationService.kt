package com.poseplz.server.ui

import com.poseplz.server.domain.file.FileNotFoundException
import com.poseplz.server.domain.file.FileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MigrationService(
    private val fileRepository: FileRepository,
) {
    @Transactional
    fun migrate(fileId: Long, width: Int, height: Int) {
        val file = fileRepository.findByIdOrNull(fileId) ?: throw FileNotFoundException("File not found. fileId: $fileId")
        file.width = width
        file.height = height
    }
}
