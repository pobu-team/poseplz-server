package com.poseplz.server.application.pose

import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.domain.file.File
import com.poseplz.server.domain.file.FileCreateVo
import com.poseplz.server.domain.file.FileService
import com.poseplz.server.domain.file.storage.StorageService
import com.poseplz.server.domain.file.storage.StorageUploadRequestVo
import com.poseplz.server.domain.pose.PoseCreateVo
import com.poseplz.server.domain.pose.PoseNotFoundException
import com.poseplz.server.domain.pose.PoseService
import com.poseplz.server.domain.pose.PoseUpdateVo
import com.poseplz.server.ui.api.pose.PoseDetailResponse
import com.poseplz.server.ui.api.pose.PoseSimpleResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.io.InputStream
import kotlin.reflect.typeOf

@Component
class PoseApplicationService(
    private val storageService: StorageService,
    private val fileService: FileService,
    private val poseService: PoseService,
) {
    fun create(
        inputStream: InputStream,
        fileUploadVo: FileUploadVo,
        tagIds: List<Long>,
        peopleCount: Int,
    ): PoseDetailResponse {
        val file = storageService.upload(
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
                )
            )
        }
        val pose = poseService.create(
            poseCreateVo = PoseCreateVo(
                fileId = file.fileId,
                tagIds = tagIds,
                peopleCount = peopleCount,
            ),
        )
        return pose.toPoseDetailResponse()
    }

    fun update(
        poseId: Long,
        inputStream: InputStream,
        fileUploadVo: FileUploadVo,
        tagIds: List<Long>,
        peopleCount: Int,
    ): PoseDetailResponse {
        val file: File? = if (inputStream.available() > 0) {
            // Delete old file
            val fileId = poseService.findById(poseId)?.file?.fileId
                ?: throw PoseNotFoundException()
            fileService.delete(fileId)

            // create new file
            createFile(inputStream, fileUploadVo)
        } else {
            null
        }

        // Update pose
        val pose = poseService.update(
            poseId = poseId,
            poseUpdateVo = PoseUpdateVo(
                fileId = file?.fileId,
                tagIds = tagIds,
                peopleCount = peopleCount,
            ),
        )

        return pose.toPoseDetailResponse()
    }

    private fun createFile(
        inputStream: InputStream,
        fileUploadVo: FileUploadVo,
    ): File {
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
                )
            )
        }
    }

    fun recommend(
        tagGroupIds: Collection<Long>,
    ): List<PoseSimpleResponse> {
        return poseService.recommend(tagGroupIds)
            .map { it.toPoseSimpleResponse() }
    }

    fun findByTagIds(
        tagIds: Collection<Long>,
        pageable: Pageable,
    ): Page<PoseSimpleResponse> {
        return poseService.findBy(tagIds, pageable)
            .map { it.toPoseSimpleResponse() }
    }

    fun findByPoseId(
        poseId: Long,
    ): PoseDetailResponse {
        return poseService.findById(poseId)?.toPoseDetailResponse()
            ?: throw PoseNotFoundException()
    }
}
