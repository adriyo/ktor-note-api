package com.adriyo.dao

import com.adriyo.models.Note
import com.adriyo.requests.UserNoteRequest

interface DAOUserNotes {
    suspend fun getNotes(userId: Int): List<Note>
    suspend fun saveNote(request: UserNoteRequest, userId: Int): Note?
    suspend fun saveNotes(listRequest: List<UserNoteRequest>, userId: Int): List<Note>?
}