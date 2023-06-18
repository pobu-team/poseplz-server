package com.poseplz.server.domain.tag.group

import com.poseplz.server.domain.tag.Tag
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
class TagGroupTag(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val tagGroupTagId: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "tagGroupId")
    val tagGroup: TagGroup,
    @ManyToOne
    @JoinColumn(name = "tagId")
    val tag: Tag,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun of(tagGroup: TagGroup, tag: Tag) = TagGroupTag(
            tagGroup = tagGroup,
            tag = tag,
        )
    }
}
