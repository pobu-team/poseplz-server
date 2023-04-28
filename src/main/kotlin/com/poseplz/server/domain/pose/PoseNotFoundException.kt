package com.poseplz.server.domain.pose

class PoseNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
