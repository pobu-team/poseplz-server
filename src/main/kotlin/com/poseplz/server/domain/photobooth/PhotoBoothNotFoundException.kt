package com.poseplz.server.domain.photobooth

class PhotoBoothNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
