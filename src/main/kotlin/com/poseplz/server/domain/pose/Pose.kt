package com.poseplz.server.domain.pose

import com.poseplz.server.domain.file.File
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(schema = "poseplz", name = "pose")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE pose SET deleted = true WHERE pose_id = ?")
class Pose(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val poseId: Long = 0L,
    val memberId: Long?,
    var peopleCount: Int,
    @ManyToOne
    @JoinColumn(name = "fileId")
    var file: File,
    @OneToMany(mappedBy = "pose", cascade = [CascadeType.ALL], orphanRemoval = true)
    val poseTags: MutableList<PoseTag> = mutableListOf(),
    var sourceTitle: String?,
    var sourceUrl: String?,
    var deleted: Boolean = false,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    @CreatedBy
    lateinit var createdBy: String

    @LastModifiedBy
    lateinit var updatedBy: String

    companion object {
        fun of(
            memberId: Long?,
            file: File,
            peopleCount: Int,
            sourceTitle: String? = null,
            sourceUrl: String? = null,
        ): Pose {
            return Pose(
                memberId = memberId,
                file = file,
                peopleCount = peopleCount,
                sourceTitle = sourceTitle,
                sourceUrl = sourceUrl
            )
        }
    }
}
