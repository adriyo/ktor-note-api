package com.adriyo.routes

import com.adriyo.models.User
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val message: String,
    val data: User
)

fun Route.userRouting() {
    route("/user") {
        userAuthRouting()
        userNoteRouting()
    }
}