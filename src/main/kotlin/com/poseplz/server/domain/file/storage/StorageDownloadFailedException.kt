package com.poseplz.server.domain.file.storage

class StorageDownloadFailedException(
    override val message: String? = null,
    override val cause: Throwable? = null,
): RuntimeException(message, cause)
