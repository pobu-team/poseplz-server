package com.poseplz.server.domain.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByProviderIdentifier(providerIdentifier: ProviderIdentifier): Member?
}
