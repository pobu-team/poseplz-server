package com.poseplz.server.domain.pose.archive

import com.poseplz.server.domain.member.MemberRepository
import com.poseplz.server.domain.pose.Pose
import com.poseplz.server.domain.pose.PoseNotFoundException
import com.poseplz.server.domain.pose.PoseQueryRequestVo
import com.poseplz.server.domain.pose.PoseRepository
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface ArchivedPoseService {
    fun getArchivedPoses(memberId: Long, poseQueryRequestVo: PoseQueryRequestVo): Slice<Pose>
    fun isArchived(memberId: Long, poseId: Long): Boolean
    fun archivePose(memberId: Long, poseId: Long): Pose
    fun unarchivePose(memberId: Long, poseId: Long)
    fun unarchiveAllPoses(memberId: Long)
}

@Service
@Transactional(readOnly = true)
class ArchivedPoseServiceImpl(
    private val archivedPoseRepository: ArchivedPoseRepository,
    private val poseRepository: PoseRepository,
    private val memberRepository: MemberRepository,
) : ArchivedPoseService {

    override fun getArchivedPoses(memberId: Long, poseQueryRequestVo: PoseQueryRequestVo): Slice<Pose> {
        return archivedPoseRepository.findBy(memberId, poseQueryRequestVo)
    }

    override fun isArchived(memberId: Long, poseId: Long): Boolean {
        return archivedPoseRepository.findByMemberIdAndPoseId(memberId, poseId) != null
    }

    @Transactional
    override fun archivePose(memberId: Long, poseId: Long): Pose {
        val alreadyArchivedPose = archivedPoseRepository.findByMemberIdAndPoseId(memberId, poseId)
        if (alreadyArchivedPose != null) {
            return alreadyArchivedPose
        }
        val pose =
            poseRepository.findByIdOrNull(poseId) ?: throw PoseNotFoundException("pose not found. poseId: $poseId")
        archivedPoseRepository.save(
            ArchivedPose.of(
                member = memberRepository.getReferenceById(memberId),
                pose = pose,
            )
        )
        return pose
    }

    @Transactional
    override fun unarchivePose(memberId: Long, poseId: Long) {
        archivedPoseRepository.deleteByMember_memberIdAndPose_poseId(memberId, poseId)
    }

    @Transactional
    override fun unarchiveAllPoses(memberId: Long) {
        archivedPoseRepository.deleteByMember_memberId(memberId)
    }
}
