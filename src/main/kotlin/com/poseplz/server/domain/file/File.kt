package com.poseplz.server.domain.file

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * TODO: hash 계산해서 중복 등록 방지
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
class File(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val fileId: Long = 0L,
    val url: String,
    val name: String,
    val contentType: String,
    val size: Long,
    val deleted: Boolean = false,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
}
