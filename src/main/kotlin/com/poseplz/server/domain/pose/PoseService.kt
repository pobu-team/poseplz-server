package com.poseplz.server.domain.pose

import com.poseplz.server.domain.file.FileRepository
import com.poseplz.server.domain.tag.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PoseService {
    fun create(poseCreateVo: PoseCreateVo): Pose
    fun update(poseId: Long, poseUpdateVo: PoseUpdateVo): Pose
    fun findAll(pageable: Pageable): Page<Pose>
    fun findBy(tagIds: List<Long>, pageable: Pageable): Page<Pose>
    fun findById(poseId: Long): Pose?
    fun getById(poseId: Long): Pose
}

@Service
@Transactional(readOnly = true)
class PoseServiceImpl(
    private val poseRepository: PoseRepository,
    private val fileRepository: FileRepository,
    private val tagRepository: TagRepository,
) : PoseService {
    @Transactional
    override fun create(poseCreateVo: PoseCreateVo): Pose {
        val file = fileRepository.getReferenceById(poseCreateVo.fileId)
        val pose = Pose.of(file)
        val poseTags = poseCreateVo.tagIds
            .map { tagRepository.getReferenceById(it) }
            .map { tag -> PoseTag.of(pose, tag) }
        pose.poseTags.addAll(poseTags)
        poseRepository.save(pose)
        return pose
    }

    @Transactional
    override fun update(poseId: Long, poseUpdateVo: PoseUpdateVo): Pose {
        val pose = poseRepository.findByIdOrNull(poseId)
            ?: throw PoseNotFoundException()
        val poseTags = poseUpdateVo.tagIds
            .map { tagRepository.getReferenceById(it) }
            .map { tag -> PoseTag.of(pose, tag) }
        pose.poseTags.clear()
        pose.poseTags.addAll(poseTags)
        return pose
    }

    override fun findAll(pageable: Pageable): Page<Pose> {
        return poseRepository.findAll(pageable)
    }

    override fun findBy(tagIds: List<Long>, pageable: Pageable): Page<Pose> {
        return poseRepository.findByPoseTags_Tag_TagIdIn(tagIds, pageable)
    }

    override fun findById(poseId: Long): Pose? {
        return poseRepository.findByIdOrNull(poseId)
    }

    override fun getById(poseId: Long): Pose {
        return poseRepository.findByIdOrNull(poseId) ?: throw PoseNotFoundException()
    }

}
