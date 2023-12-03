package com.poseplz.server.infrastructure.naver

class NaverApiException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
