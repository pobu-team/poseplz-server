package com.haeseong.pobuserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PobuServerApplication

fun main(args: Array<String>) {
    runApplication<PobuServerApplication>(*args)
}
