package com.adriyo.routes

import com.adriyo.dao.DatabaseFactory
import com.adriyo.requests.AuthRequest
import com.adriyo.requests.UserRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userAuthRouting() {
    post("/register") {
        val user = call.receive<UserRequest>()
        val userResult = DatabaseFactory.daoUser.register(user) ?: throw BadRequestException("Gagal menyimpan data")
        call.respond(
            status = HttpStatusCode.Created,
            UserResponse(message = "Data berhasil disimpan", data = userResult)
        )
    }
    post("/login") {
        val user = call.receive<AuthRequest>()
        val userResult = DatabaseFactory.daoUser.login(user) ?: throw BadRequestException("Gagal memproses data")
        call.respond(
            status = HttpStatusCode.OK, UserResponse(
                message = "User berhasil login",
                data = userResult
            )
        )
    }
}