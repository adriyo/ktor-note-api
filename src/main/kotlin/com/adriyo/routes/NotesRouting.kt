package com.adriyo.routes

import com.adriyo.models.ApiResult
import com.adriyo.models.Note
import io.ktor.http.*
import com.adriyo.models.noteStorage
import com.adriyo.requests.NoteRequest
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.serialization.SerializationException

fun Route.noteRouting() {
    install(RequestValidation) {
        validate<NoteRequest> { note ->
            if (note.id == null) {
                ValidationResult.Invalid("ID tidak boleh kosong")
            } else if (note.title.isNullOrEmpty()) {
                ValidationResult.Invalid("Judul tidak boleh kosong")
            } else if (note.description.isNullOrEmpty()) {
                ValidationResult.Invalid("Deskripsi tidak boleh kosong")
            } else {
                ValidationResult.Valid
            }
        }
    }
    route("/notes") {
        get {
            if (noteStorage.isNotEmpty()) {
                call.respond(noteStorage)
            } else {
                call.respond(status = HttpStatusCode.OK, ApiResult("Data not available"))
            }
        }
        get("{id?}") {
            val id: String = call.parameters["id"] ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                ApiResult("Missing ID")
            )
            val note: Note = noteStorage.find { it.id == id.toInt() } ?: return@get call.respond(
                HttpStatusCode.NotFound,
                ApiResult("Data tidak ditemukan")
            )
            call.respond(note)
        }
        post {
            val notes =
                call.receiveNullable<List<NoteRequest>>()
                    ?: throw BadRequestException("Tidak ada data yang dapat diproses")
            noteStorage.addAll(notes.map { it.toNote() })
            call.respond(status = HttpStatusCode.Created, ApiResult("Notes saved"))
        }
    }
    route("/note") {
        post {
            val note =
                call.receiveNullable<NoteRequest>() ?: throw BadRequestException("Tidak ada data yang dapat diproses")
            noteStorage.add(note.toNote())
            call.respond(status = HttpStatusCode.Created, ApiResult("Note saved"))
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (noteStorage.removeIf { it.id == id.toInt() }) {
                call.respond(status = HttpStatusCode.Accepted, ApiResult("Catatan berhasil dihapus"))
            } else {
                call.respond(status = HttpStatusCode.NotFound, ApiResult("Not found"))
            }
        }
        put {
            val note =
                call.receiveNullable<NoteRequest>() ?: throw BadRequestException("Tidak ada data yang dapat diproses")
            val id = note.id ?: -1
            noteStorage.find { it.id == id }?.also {
                it.title = note.title ?: ""
                it.description = note.description ?: ""
            }
            call.respond(status = HttpStatusCode.Created, ApiResult("Note updated"))
        }
    }
}