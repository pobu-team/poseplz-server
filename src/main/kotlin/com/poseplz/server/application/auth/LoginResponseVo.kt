package com.poseplz.server.application.auth

import com.poseplz.server.domain.member.MemberVo

data class LoginResponseVo(
    val memberVo: MemberVo,
    val accessToken: String,
)
