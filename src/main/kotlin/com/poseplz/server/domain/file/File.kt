package com.poseplz.server.domain.file

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
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
    @Enumerated(EnumType.STRING)
    var imageType: FileImageType,
    val size: Long,
    var width: Int? = null,
    var height: Int? = null,
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
        fun from(fileCreateVo: FileCreateVo): File {
            return File(
                url = fileCreateVo.url,
                name = fileCreateVo.name,
                contentType = fileCreateVo.contentType,
                imageType = FileImageType.PHOTOGRAPH,
                size = fileCreateVo.size,
            )
        }
    }

    fun delete() {
        this.deleted = true
    }
}
