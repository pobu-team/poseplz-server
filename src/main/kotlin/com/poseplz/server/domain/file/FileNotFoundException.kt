package com.poseplz.server.domain.file

class FileNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
