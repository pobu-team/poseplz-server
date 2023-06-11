package com.poseplz.server.domain.tag.group

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
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
    var deleted: Boolean = false,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun from(tagGroupCreateVo: TagGroupCreateVo): TagGroup {
            return TagGroup(
                name = tagGroupCreateVo.name,
            )
        }
    }

    fun update(tagGroupUpdateVo: TagGroupUpdateVo) {
        this.name = tagGroupUpdateVo.name
    }
}
