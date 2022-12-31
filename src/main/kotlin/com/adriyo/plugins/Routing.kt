package com.adriyo.plugins

import com.adriyo.routes.noteRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        noteRouting()
        get("/") {
            call.respondText("Hello World!")
        }
    }
}