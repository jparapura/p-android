package com.januszpol

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.januszpol.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureMonitoring()
        configureRouting()
    }.start(wait = true)
}
