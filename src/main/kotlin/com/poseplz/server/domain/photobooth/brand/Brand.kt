package com.poseplz.server.domain.photobooth.brand

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Brand(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val brandId: Long = 0L,
    /**
     * 브랜드 이름
     */
    var name: String,
    /**
     * 브랜드 설명
     */
    var description: String?,
    /**
     * 브랜드 로고 이미지 URL
     */
    var logoUrl: String?,
    /**
     * 브랜드 홈페이지 URL
     */
    var homepageUrl: String?,
    /**
     * 브랜드 인스타그램 URL
     */
    var instagramUrl: String?,
    /**
     * 삭제 여부
     */
    var deleted: Boolean = false
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
}
