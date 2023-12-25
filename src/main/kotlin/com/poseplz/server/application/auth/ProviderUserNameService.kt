package com.poseplz.server.application.auth

interface ProviderUserNameService {
    fun getProviderUserName(loginRequestVo: LoginRequestVo): String?
    fun supports(loginRequestVo: LoginRequestVo): Boolean
}
