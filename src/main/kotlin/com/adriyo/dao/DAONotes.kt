package com.adriyo.dao

import com.adriyo.models.Note
import com.adriyo.requests.NoteRequest

interface DAONotes {
    suspend fun allNotes(): List<Note>
    suspend fun note(id: Int): Note?
    suspend fun save(request: NoteRequest): Note?
    suspend fun saveNotes(listRequest: List<NoteRequest>): List<Note>?
    suspend fun update(request: NoteRequest): Boolean
    suspend fun delete(id: Int): Boolean
}