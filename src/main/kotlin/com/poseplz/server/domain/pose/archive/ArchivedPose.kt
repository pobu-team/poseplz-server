package com.poseplz.server.domain.pose.archive

import com.poseplz.server.domain.member.Member
import com.poseplz.server.domain.pose.Pose
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class ArchivedPose(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val archivedPoseId: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "memberId")
    val member: Member,
    @ManyToOne
    @JoinColumn(name = "poseId")
    val pose: Pose,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun of(
            member: Member,
            pose: Pose,
        ): ArchivedPose {
            return ArchivedPose(
                member = member,
                pose = pose,
            )
        }
    }
}
