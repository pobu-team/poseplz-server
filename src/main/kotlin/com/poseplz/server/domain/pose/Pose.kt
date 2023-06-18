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
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Pose(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val poseId: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "fileId")
    var file: File,
    @OneToMany(mappedBy = "pose", cascade = [CascadeType.ALL], orphanRemoval = true)
    val poseTags: MutableList<PoseTag> = mutableListOf(),
    var deleted: Boolean = false,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun of(
            file: File,
        ): Pose {
            return Pose(
                file = file,
            )
        }
    }
}
