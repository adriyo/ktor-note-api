package com.adriyo.routes

import com.adriyo.dao.DatabaseFactory.dao
import com.adriyo.models.ApiResult
import com.adriyo.models.Note
import com.adriyo.requests.NoteRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.noteRouting() {
    route("/notes") {
        get {
            val notes = dao.allNotes()
            call.respond(notes)
        }
        get("{id?}") {
            val id: String = call.parameters["id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                ApiResult("Missing ID")
            )
            val note: Note = dao.note(id.toInt()) ?: return@get call.respond(
                HttpStatusCode.NotFound,
                ApiResult("Data tidak ditemukan")
            )
            call.respond(note)
        }
        post {
            val notes =
                call.receiveNullable<List<NoteRequest>>()
                    ?: throw BadRequestException("Tidak ada data yang dapat diproses")
            dao.saveNotes(notes)
            call.respond(status = HttpStatusCode.Created, ApiResult("Notes saved"))
        }
    }
    route("/note") {
        post {
            val note = call.receive<NoteRequest>()
            dao.save(note) ?: throw BadRequestException("Gagal menyimpan data")
            call.respond(status = HttpStatusCode.Created, ApiResult("Data berhasil disimpan"))
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val isSuccess = dao.delete(id.toInt())
            if (isSuccess) {
                call.respond(status = HttpStatusCode.Accepted, ApiResult("Catatan berhasil dihapus"))
            } else {
                call.respond(status = HttpStatusCode.NotFound, ApiResult("Not found"))
            }
        }
        put {
            val note =
                call.receiveNullable<NoteRequest>() ?: throw BadRequestException("Tidak ada data yang dapat diproses")
            val isSuccess: Boolean = dao.update(note)
            if (!isSuccess) throw BadRequestException("Tidak ada data yang dapat diproses")
            call.respond(status = HttpStatusCode.Created, ApiResult("Note updated"))
        }
    }
}