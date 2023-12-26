package com.poseplz.server.domain.member

data class MemberVo(
    val memberId: Long,
    val name: String?,
    val profileImageUrl: String?,
    val status: MemberStatus,
) {
    companion object {
        fun from(member: Member) = MemberVo(
            memberId = member.memberId,
            name = member.name,
            profileImageUrl = member.profileImageUrl,
            status = member.status
        )
    }
}
