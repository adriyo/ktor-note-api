package com.adriyo.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserNoteRequest(
    val title: String,
    val description: String
)
