package com.poseplz.server.domain.tag.group

class TagGroupNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause) {
}
