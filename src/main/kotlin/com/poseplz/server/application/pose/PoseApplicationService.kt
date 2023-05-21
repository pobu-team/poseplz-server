package com.poseplz.server.application.pose

import com.poseplz.server.application.file.FileUploadVo
import com.poseplz.server.domain.file.FileCreateVo
import com.poseplz.server.domain.file.FileService
import com.poseplz.server.domain.file.storage.StorageService
import com.poseplz.server.domain.file.storage.StorageUploadRequestVo
import com.poseplz.server.domain.pose.PoseCreateVo
import com.poseplz.server.domain.pose.PoseNotFoundException
import com.poseplz.server.domain.pose.PoseService
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
) {
    fun create(
        inputStream: InputStream,
        fileUploadVo: FileUploadVo,
        tagIds: List<Long>,
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
            ),
        )
        return pose.toPoseDetailResponse()
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
