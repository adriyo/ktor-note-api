package com.adriyo.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String?,
    val username: String?,
    val password: String?
)

@Serializable
data class AuthRequest(
    val username: String?,
    val password: String?
)

