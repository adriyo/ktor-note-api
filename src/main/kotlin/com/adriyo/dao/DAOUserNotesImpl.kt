package com.adriyo.dao

import com.adriyo.dao.entities.NoteEntity
import com.adriyo.dao.entities.UserEntity
import com.adriyo.models.Note
import com.adriyo.requests.UserNoteRequest
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction

class DAOUserNotesImpl : DAOUserNotes {

    override suspend fun getNotes(userId: Int): List<Note> = transaction {
        val user = UserEntity[userId]
        user.notes.map(::resultEntityToNote)
    }

    override suspend fun saveNote(request: UserNoteRequest, userId: Int): Note = transaction {
        val user = UserEntity[userId]
        val newNote = NoteEntity.new {
            title = request.title
            description = request.description
        }

        user.notes = SizedCollection(user.notes + listOf(newNote))
        resultEntityToNote(newNote)
    }

    override suspend fun saveNotes(listRequest: List<UserNoteRequest>, userId: Int): List<Note> = transaction{
        val user = UserEntity[userId]
        val resultNotes = mutableListOf<NoteEntity>()
        for (request in listRequest) {
            val newNote = NoteEntity.new {
                title = request.title
                description = request.description
            }
            resultNotes.add(newNote)
        }

        user.notes = SizedCollection(user.notes + resultNotes)
        user.notes.map(::resultEntityToNote)
    }

    private fun resultEntityToNote(row: NoteEntity) = Note(
        id = row.id.value,
        title = row.title,
        description = row.description
    )
}