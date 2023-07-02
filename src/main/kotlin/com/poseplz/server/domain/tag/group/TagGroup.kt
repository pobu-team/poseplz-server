package com.poseplz.server.domain.tag.group

import com.poseplz.server.domain.tag.Tag
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class TagGroup(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val tagGroupId: Long = 0L,
    var name: String,
    @ElementCollection
    val peopleCounts: MutableList<Int> = mutableListOf(),
    @OneToMany(mappedBy = "tagGroup", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tagGroupTags: MutableList<TagGroupTag> = mutableListOf(),
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
            tagGroupCreateVo: TagGroupCreateVo,
            tags: List<Tag>,
        ): TagGroup {
            return TagGroup(
                name = tagGroupCreateVo.name,
            ).apply {
                this.peopleCounts.addAll(tagGroupCreateVo.peopleCounts)
                this.tagGroupTags.addAll(tags.map { TagGroupTag.of(this, it) })
            }
        }
    }

    fun update(
        tagGroupUpdateVo: TagGroupUpdateVo,
        tagGroupTags: List<TagGroupTag>,
    ) {
        this.name = tagGroupUpdateVo.name
        this.peopleCounts.clear()
        this.peopleCounts.addAll(tagGroupUpdateVo.peopleCounts)
        this.tagGroupTags.clear()
        this.tagGroupTags.addAll(tagGroupTags)
    }
}
