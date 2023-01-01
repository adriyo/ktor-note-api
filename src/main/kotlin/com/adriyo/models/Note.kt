package com.adriyo.models

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    var title: String,
    var description: String
)