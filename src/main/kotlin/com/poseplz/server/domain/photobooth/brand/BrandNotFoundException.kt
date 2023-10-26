package com.poseplz.server.domain.photobooth.brand

class BrandNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
