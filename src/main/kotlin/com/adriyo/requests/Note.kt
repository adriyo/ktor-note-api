package com.adriyo.requests

import com.adriyo.models.Note
import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val id: Int? = null,
    val title: String?,
    val description: String?
) {
    fun toNote(): Note {
        return Note(
            id = id ?: -1,
            title = title ?: "",
            description = description ?: ""
        )
    }
}

