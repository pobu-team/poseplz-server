package com.poseplz.server.ui.api.member

import com.poseplz.server.domain.member.MemberVo
import com.poseplz.server.ui.api.member.MemberResponse as MemberResponse1

data class MemberResponse(
    val memberId: String,
    val status: String,
) {
    companion object {
        fun from(memberVo: MemberVo) = MemberResponse1(
            memberId = memberVo.memberId.toString(),
            status = memberVo.status.name,
        )
    }
}
