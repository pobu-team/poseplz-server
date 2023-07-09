package com.poseplz.server.application.member

import com.poseplz.server.domain.member.MemberService
import com.poseplz.server.domain.member.MemberVo
import org.springframework.stereotype.Component

@Component
class MemberApplicationService(
    private val memberService: MemberService,
) {
    fun getMember(memberId: Long): MemberVo {
        return memberService.findById(memberId)
            ?.let { MemberVo.from(it) }
            ?: throw IllegalArgumentException("Member not found. memberId: $memberId")
    }
}
