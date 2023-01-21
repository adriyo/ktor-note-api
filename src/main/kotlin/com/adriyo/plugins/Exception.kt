package com.adriyo.plugins

import com.adriyo.models.ApiResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureException() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is BadRequestException -> {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        ApiResult(message = "Gagal memproses data")
                    )
                }
                is UnsupportedMediaTypeException -> {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        ApiResult(message = "Unsupported media type")
                    )
                }

                is CannotTransformContentToTypeException -> {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        ApiResult(message = "Tidak ada data yang dapat diproses")
                    )
                }

                else -> {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        ApiResult(message = "${cause.message}")
                    )
                }
            }
        }
    }
}