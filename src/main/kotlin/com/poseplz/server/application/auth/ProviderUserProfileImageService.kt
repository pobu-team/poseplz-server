package com.poseplz.server.application.auth

interface ProviderUserProfileImageService {
    fun getProviderUserProfileImage(loginRequestVo: LoginRequestVo): String?
    fun supports(loginRequestVo: LoginRequestVo): Boolean
}
