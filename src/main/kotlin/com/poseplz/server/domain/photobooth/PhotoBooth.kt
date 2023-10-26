package com.poseplz.server.domain.photobooth

import com.poseplz.server.domain.photobooth.brand.Brand
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class PhotoBooth(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val photoBoothId: Long = 0L,
    /**
     * 포토부스 이름
     */
    var name: String,
    /**
     * 포토부스 브랜드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId")
    var brand: Brand,
    /**
     * 포토부스 설명
     */
    var description: String?,
    /**
     * 대표 이미지 url
     */
    var imgeUrl: String?,
    /**
     * 포토부스 주소
     */
    var address: String,
    /**
     * 포토부스 좌표
     */
    @Embedded
    var coordinates: Coordinates,
    /**
     * 삭제 여부
     */
    var deleted: Boolean = false,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
}
