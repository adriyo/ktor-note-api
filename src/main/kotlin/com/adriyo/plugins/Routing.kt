package com.adriyo.plugins

import com.adriyo.routes.noteRouting
import com.adriyo.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        noteRouting()
        userRouting()
    }
}
