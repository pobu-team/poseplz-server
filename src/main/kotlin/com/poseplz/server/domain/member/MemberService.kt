package com.poseplz.server.domain.member

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface MemberService {
    fun create(providerIdentifier: ProviderIdentifier): Member

    fun update(
        memberId: Long,
        name: String?,
        profileImageUrl: String?,
    ): Member

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

    @Transactional
    override fun update(
        memberId: Long,
        name: String?,
        profileImageUrl: String?
    ): Member {
        return memberRepository.findByIdOrNull(memberId)
            ?.apply {
                this.name = name
                this.profileImageUrl = profileImageUrl
            }
            ?: throw MemberNotFoundException("존재하지 않는 회원입니다. memberId: $memberId")
    }

    override fun findById(memberId: Long): Member? {
        return memberRepository.findByIdOrNull(memberId)
    }

    override fun findByProviderIdentifier(providerIdentifier: ProviderIdentifier): Member? {
        return memberRepository.findByProviderIdentifier(providerIdentifier)
    }
}
