package com.poseplz.server.infrastructure.kakao

class KakaoApiFailedException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
