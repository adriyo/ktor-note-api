package com.adriyo.dao

import com.adriyo.dao.DatabaseFactory.dbQuery
import com.adriyo.dao.entities.Notes
import com.adriyo.models.Note
import com.adriyo.requests.NoteRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DAONotesImpl : DAONotes {

    override suspend fun allNotes(): List<Note> = dbQuery {
        Notes.selectAll().map(::resultRowToNote)
    }

    override suspend fun note(id: Int): Note? = dbQuery {
        Notes
            .select { Notes.id eq id }
            .map(::resultRowToNote)
            .singleOrNull()
    }

    override suspend fun save(request: NoteRequest): Note? = dbQuery {
        val insertStatement = Notes.insert {
            it[title] = request.title ?: ""
            it[description] = request.description ?: ""
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNote)
    }

    override suspend fun saveNotes(listRequest: List<NoteRequest>): List<Note>? {
        transaction {
            addLogger(StdOutSqlLogger)
            val batchInsert = Notes.batchInsert(data = listRequest, shouldReturnGeneratedValues = true) { item ->
                this[Notes.title] = item.title ?: ""
                this[Notes.description] = item.description ?: ""
            }
            return@transaction batchInsert.map(::resultRowToNote)
        }
        return null
    }

    override suspend fun update(request: NoteRequest): Boolean = dbQuery {
        Notes.update({ Notes.id eq (request.id ?: -1) }) {
            it[title] = request.title ?: ""
            it[description] = request.description ?: ""
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        Notes.deleteWhere { Notes.id eq id } > 0
    }

    private fun resultRowToNote(row: ResultRow) = Note(
        id = row[Notes.id],
        title = row[Notes.title],
        description = row[Notes.description]
    )
}