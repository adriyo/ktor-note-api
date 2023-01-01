package com.adriyo

import com.adriyo.dao.DatabaseFactory
import com.adriyo.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureRouting()
    configureException()
}
