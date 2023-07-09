package com.poseplz.server.domain.member

data class MemberVo(
    val memberId: Long,
    val status: MemberStatus,
) {
    companion object {
        fun from(member: Member) = MemberVo(
            memberId = member.memberId,
            status = member.status
        )
    }
}
