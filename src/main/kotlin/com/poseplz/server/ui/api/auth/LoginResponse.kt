package com.poseplz.server.ui.api.auth

import com.poseplz.server.ui.api.member.MemberResponse

data class LoginResponse(
    val member: MemberResponse,
    val accessToken: String
)
