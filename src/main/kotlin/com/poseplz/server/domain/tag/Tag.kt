package com.poseplz.server.domain.tag

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Tag(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val tagId: Long = 0L,
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
}
