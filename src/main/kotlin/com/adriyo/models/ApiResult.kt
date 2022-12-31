package com.adriyo.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResult(
    val message: String
)