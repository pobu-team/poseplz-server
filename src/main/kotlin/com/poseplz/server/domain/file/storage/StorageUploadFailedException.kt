package com.poseplz.server.domain.file.storage

class StorageUploadFailedException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)
