package com.poseplz.server.domain.member

class MemberNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
