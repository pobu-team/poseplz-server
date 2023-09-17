package com.poseplz.server.domain.tag

import com.poseplz.server.domain.pose.PoseTag
import com.poseplz.server.domain.tag.group.TagGroupTag
import jakarta.persistence.*
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
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE tag SET deleted = 1 WHERE tag_id = ?")
class Tag(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val tagId: Long = 0L,
    @OneToMany(mappedBy = "tag", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tagGroupTags: MutableList<TagGroupTag> = mutableListOf(),
    @OneToMany(mappedBy = "tag", cascade = [CascadeType.ALL], orphanRemoval = true)
    val poseTags: MutableList<PoseTag> = mutableListOf(),
    @Enumerated(EnumType.STRING)
    var type: TagType,
    var name: String,
    var selectorName: String,
    var selectorDisplayOrder: Int,
    var emojiImageUrl: String? = null,
    var emojiText: String? = null,
    var description: String? = null,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (tagId != other.tagId) return false

        return true
    }

    override fun hashCode(): Int {
        return tagId.hashCode()
    }

    fun update(tagUpdateVo: TagUpdateVo) {
        type = tagUpdateVo.tagType
        name = tagUpdateVo.name
        selectorName = tagUpdateVo.selectorName
        selectorDisplayOrder = tagUpdateVo.selectorDisplayOrder
        emojiImageUrl = tagUpdateVo.emojiImageUrl
        emojiText = tagUpdateVo.emojiText
        description = tagUpdateVo.description
    }

    companion object {
        fun from(tagCreateVo: TagCreateVo): Tag {
            return Tag(
                type = tagCreateVo.tagType,
                name = tagCreateVo.name,
                selectorName = tagCreateVo.selectorName,
                selectorDisplayOrder = tagCreateVo.selectorDisplayOrder,
                emojiImageUrl = tagCreateVo.emojiImageUrl,
                emojiText = tagCreateVo.emojiText,
                description = tagCreateVo.description,
            )
        }
    }

    fun delete() {
        deleted = true
    }
}
