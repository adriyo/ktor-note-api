package com.adriyo.dao

import com.adriyo.models.User
import com.adriyo.requests.AuthRequest
import com.adriyo.requests.UserRequest

interface DAOUsers {
    suspend fun register(request: UserRequest): User?
    suspend fun login(request: AuthRequest): User?
}