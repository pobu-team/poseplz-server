package com.poseplz.server.domain.member

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class ProviderIdentifier(
    @Enumerated(EnumType.STRING)
    val providerType: ProviderType,
    val providerUserId: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProviderIdentifier

        if (providerType != other.providerType) return false
        if (providerUserId != other.providerUserId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = providerType.hashCode()
        result = 31 * result + providerUserId.hashCode()
        return result
    }
}
