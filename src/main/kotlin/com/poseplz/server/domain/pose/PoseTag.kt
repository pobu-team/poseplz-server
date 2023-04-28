package com.poseplz.server.domain.pose

import com.poseplz.server.domain.tag.Tag
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class PoseTag(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val poseTagId: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "poseId")
    val pose: Pose,
    @ManyToOne
    @JoinColumn(name = "tagId")
    val tag: Tag,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun of(pose: Pose, tag: Tag): PoseTag {
            return PoseTag(pose = pose, tag = tag)
        }
    }
}
