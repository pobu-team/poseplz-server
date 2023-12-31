package com.poseplz.server.domain.tag

class TagNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
