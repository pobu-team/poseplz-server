package com.poseplz.server.domain.member

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(generator = "SnowflakeIdentifierGenerator")
    @GenericGenerator(
        name = "SnowflakeIdentifierGenerator",
        strategy = "com.poseplz.server.infrastructure.hibernate.SnowflakeIdentifierGenerator",
    )
    val memberId: Long = 0L,
    var name: String? = null,
    var profileImageUrl: String? = null,
    @Enumerated(EnumType.STRING)
    var status: MemberStatus,
    @Embedded
    val providerIdentifier: ProviderIdentifier,
) {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun from(providerIdentifier: ProviderIdentifier): Member {
            return Member(
                status = MemberStatus.MEMBER,
                providerIdentifier = providerIdentifier,
            )
        }
    }
}
