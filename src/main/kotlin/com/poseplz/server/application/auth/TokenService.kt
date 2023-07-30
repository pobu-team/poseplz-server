package com.poseplz.server.application.auth

interface TokenService<T> {
    fun encode(memberId: T): String
    fun decode(token: String): T
}
