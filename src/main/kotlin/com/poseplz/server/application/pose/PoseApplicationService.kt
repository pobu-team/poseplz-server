package com.poseplz.server.application.pose

import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.domain.file.File
import com.poseplz.server.domain.file.FileCreateVo
import com.poseplz.server.domain.file.FileService
import com.poseplz.server.domain.file.storage.StorageService
import com.poseplz.server.domain.file.storage.StorageUploadRequestVo
import com.poseplz.server.domain.pose.*
import com.poseplz.server.domain.pose.archive.ArchivedPoseService
import com.poseplz.server.ui.api.pose.PoseDetailResponse
import com.poseplz.server.ui.api.pose.PoseSimpleResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class PoseApplicationService(
    private val storageService: StorageService,
    private val fileService: FileService,
    private val poseService: PoseService,
    private val archivedPoseService: ArchivedPoseService,
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
            memberId = null,
            poseCreateVo = PoseCreateVo(
                fileId = file.fileId,
                tagIds = tagIds,
                peopleCount = peopleCount,
            ),
        )
        // FIXME: memberId
        val memberId: Long? = null
        val archived = memberId?.let {
            archivedPoseService.isArchived(memberId = it, poseId = pose.poseId)
        } ?: false
        return pose.toPoseDetailResponse(archived)
    }

    fun createByMember(
        memberId: Long,
        poseCreateVo: PoseCreateVo,
    ): PoseDetailResponse {
        val pose = poseService.create(memberId, poseCreateVo)
        return pose.toPoseDetailResponse(archived = false)
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

        return pose.toPoseDetailResponse(false)
    }

    fun updateByMember(
        memberId: Long,
        poseId: Long,
        poseUpdateVo: PoseUpdateVo,
    ): PoseDetailResponse {
        val pose = poseService.findById(poseId) ?: throw PoseNotFoundException()
        if (pose.memberId != memberId) {
            throw PoseNotFoundException()
        }
        return poseService.update(
            poseId = poseId,
            poseUpdateVo = poseUpdateVo,
        ).toPoseDetailResponse(
            archived = archivedPoseService.isArchived(memberId, poseId),
        )
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
                ),
            )
        }
    }

    fun recommend(
        tagGroupIds: Collection<Long>,
        peopleCount: Int,
    ): List<PoseSimpleResponse> {
        return poseService.recommend(tagGroupIds, peopleCount)
            .map { it.toPoseSimpleResponse(false) }
    }

    fun count(): Long {
        return poseService.count()
    }

    fun findByTagIds(
        tagIds: Collection<Long>,
        pageable: Pageable,
    ): Page<PoseSimpleResponse> {
        return poseService.findBy(tagIds, pageable)
            .map { it.toPoseSimpleResponse(false) }
    }

    fun findByTagIds(
        poseQueryRequestVo: PoseQueryRequestVo,
    ): Page<PoseSimpleResponse> {
        return poseService.findBy(poseQueryRequestVo)
            .map { it.toPoseSimpleResponse(false) }
    }

    fun findByPoseId(
        poseId: Long,
    ): PoseDetailResponse {
        return poseService.findById(poseId)?.toPoseDetailResponse(false)
            ?: throw PoseNotFoundException()
    }

    fun findByPoses(
        memberId: Long,
        pageable: Pageable,
    ): Page<PoseSimpleResponse> {
        return poseService.findByMemberId(memberId, pageable)
            .map { it.toPoseSimpleResponse(
                archived = archivedPoseService.isArchived(
                    memberId = memberId,
                    poseId = it.poseId,
                )
            ) }
    }

    fun delete(
        memberId: Long,
        poseId: Long,
    ) {
        val pose = poseService.findById(poseId) ?: return
        if (pose.memberId != memberId) {
            return
        }
        poseService.delete(memberId, poseId)
    }
}
