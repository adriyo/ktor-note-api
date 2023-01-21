package com.adriyo.routes

import com.adriyo.dao.DatabaseFactory
import com.adriyo.models.ApiResult
import com.adriyo.requests.UserNoteRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userNoteRouting(){
    post("/note") {
        val userId: String = call.request.headers["Authorization"] ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            ApiResult("Missing ID")
        )
        val note = call.receive<UserNoteRequest>()
        val noteRequest = UserNoteRequest(
            title = note.title,
            description = note.description
        )
        DatabaseFactory.daoUserNotes.saveNote(request = noteRequest, userId = userId.toInt())
            ?: throw BadRequestException("Gagal menyimpan data")
        call.respond(status = HttpStatusCode.Created, ApiResult("Data berhasil disimpan"))
    }
    post("/notes") {
        val userId: String = call.request.headers["Authorization"] ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            ApiResult("Missing ID")
        )
        val notes =
            call.receiveNullable<List<UserNoteRequest>>()
                ?: throw BadRequestException("Tidak ada data yang dapat diproses")
        DatabaseFactory.daoUserNotes.saveNotes(listRequest = notes, userId = userId.toInt())
            ?: throw BadRequestException("Gagal menyimpan data")
        call.respond(status = HttpStatusCode.Created, ApiResult("Data berhasil disimpan"))
    }
    get("/notes") {
        val userId: String = call.request.headers["Authorization"] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            ApiResult("Missing ID")
        )
        val notes = DatabaseFactory.daoUserNotes.getNotes(userId.toInt())
        call.respond(notes)
    }
}