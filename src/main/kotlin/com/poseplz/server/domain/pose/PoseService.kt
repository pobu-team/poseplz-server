package com.poseplz.server.domain.pose

import com.poseplz.server.domain.file.FileRepository
import com.poseplz.server.domain.pose.archive.ArchivedPoseRepository
import com.poseplz.server.domain.tag.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface PoseService {
    fun create(memberId: Long?, poseCreateVo: PoseCreateVo): Pose
    fun update(poseId: Long, poseUpdateVo: PoseUpdateVo): Pose
    fun delete(memberId: Long, poseId: Long)
    fun deleteByAdmin(poseId: Long)
    fun recommend(tagGroupIds: Collection<Long>, peopleCount: Int): List<Pose>
    fun findAll(pageable: Pageable): Page<Pose>
    fun findBy(tagIds: Collection<Long>, pageable: Pageable): Page<Pose>
    fun findBy(poseQueryRequestVo: PoseQueryRequestVo): Page<Pose>
    fun findById(poseId: Long): Pose?
    fun getById(poseId: Long): Pose
    fun findByMemberId(memberId: Long, pageable: Pageable): Page<Pose>
    fun countByTagId(tagId: Long): Long
    fun count(): Long
}

@Service
@Transactional(readOnly = true)
class PoseServiceImpl(
    private val poseRepository: PoseRepository,
    private val fileRepository: FileRepository,
    private val tagRepository: TagRepository,
    private val archivedPoseRepository: ArchivedPoseRepository,
) : PoseService {
    @Transactional
    override fun create(
        memberId: Long?,
        poseCreateVo: PoseCreateVo,
    ): Pose {
        val file = fileRepository.getReferenceById(poseCreateVo.fileId)
        val pose = Pose.of(memberId, file, poseCreateVo.peopleCount)
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
        poseUpdateVo.fileId?.run { pose.file = fileRepository.getReferenceById(this) }
        pose.peopleCount = poseUpdateVo.peopleCount
        pose.poseTags.clear()
        pose.poseTags.addAll(
            poseUpdateVo.tagIds
                .map { tagRepository.getReferenceById(it) }
                .map { tag -> PoseTag.of(pose, tag) }
        )
        return pose
    }

    @Transactional
    override fun delete(
        memberId: Long,
        poseId: Long,
    ) {
        if (poseRepository.existsById(poseId)) {
            archivedPoseRepository.findByMember_memberIdAndPose_poseId(memberId, poseId)?.let {
                archivedPoseRepository.delete(it)
            }
            poseRepository.deleteById(poseId)
        }
    }

    @Transactional
    override fun deleteByAdmin(
        poseId: Long,
    ) {
        if (poseRepository.existsById(poseId)) {
            archivedPoseRepository.findByPose_poseId(poseId).forEach { archivedPoseRepository.delete(it) }
            poseRepository.deleteById(poseId)
        }
    }

    override fun recommend(
        tagGroupIds: Collection<Long>,
        peopleCount: Int,
    ): List<Pose> {
        return poseRepository.findByTagGroupIdsAndPeopleCount(tagGroupIds, peopleCount)
    }

    override fun findAll(pageable: Pageable): Page<Pose> {
        return poseRepository.findAll(pageable)
    }

    override fun findBy(tagIds: Collection<Long>, pageable: Pageable): Page<Pose> {
        return if (tagIds.isEmpty()) {
            poseRepository.findAll(pageable)
        } else {
            poseRepository.findByPoseTags_Tag_TagIdIn(tagIds, pageable)
        }
    }

    override fun findBy(poseQueryRequestVo: PoseQueryRequestVo): Page<Pose> {
        return if (poseQueryRequestVo.tagIds.isEmpty()) {
            poseRepository.findAll(poseQueryRequestVo.pageable)
        } else {
            poseRepository.findByPoseTags_Tag_TagIdIn(poseQueryRequestVo.tagIds, poseQueryRequestVo.pageable)
        }
    }

    override fun findById(poseId: Long): Pose? {
        return poseRepository.findByIdOrNull(poseId)
    }

    override fun getById(poseId: Long): Pose {
        return poseRepository.findByIdOrNull(poseId) ?: throw PoseNotFoundException()
    }

    override fun findByMemberId(memberId: Long, pageable: Pageable): Page<Pose> {
        return poseRepository.findByMemberId(memberId, pageable)
    }

    override fun countByTagId(tagId: Long): Long {
        return poseRepository.countByPoseTags_Tag_TagId(tagId)
    }

    override fun count(): Long {
        return poseRepository.count()
    }
}
