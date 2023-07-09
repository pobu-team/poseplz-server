package com.poseplz.server.domain.member

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface MemberService {
    fun create(providerIdentifier: ProviderIdentifier): Member

    fun findById(memberId: Long): Member?

    fun findByProviderIdentifier(providerIdentifier: ProviderIdentifier): Member?
}

@Service
@Transactional(readOnly = true)
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
) : MemberService {

    @Transactional
    override fun create(providerIdentifier: ProviderIdentifier): Member {
        val member = Member.from(
            providerIdentifier = providerIdentifier,
        )
        return memberRepository.save(member)
    }

    override fun findById(memberId: Long): Member? {
        return memberRepository.findByIdOrNull(memberId)
    }

    override fun findByProviderIdentifier(providerIdentifier: ProviderIdentifier): Member? {
        return memberRepository.findByProviderIdentifier(providerIdentifier)
    }
}
