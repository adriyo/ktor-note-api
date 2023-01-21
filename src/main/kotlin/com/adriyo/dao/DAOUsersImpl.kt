package com.adriyo.dao

import com.adriyo.dao.DatabaseFactory.dbQuery
import com.adriyo.dao.tables.Users
import com.adriyo.models.User
import com.adriyo.requests.AuthRequest
import com.adriyo.requests.UserRequest
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class DAOUsersImpl : DAOUsers {

    override suspend fun register(request: UserRequest): User? = dbQuery {
        val isUserExist = checkIfUserIsAlreadyRegistered(request)
        if (isUserExist) {
            throw Exception("User sudah terdaftar")
        }
        val insertStatement = Users.insert {
            it[name] = request.name ?: ""
            it[username] = request.username ?: ""
            it[password] = request.password ?: ""
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    private fun checkIfUserIsAlreadyRegistered(request: UserRequest): Boolean {
        val user = Users
            .select { Users.username eq (request.username ?: "") }
            .map(::resultRowToUser)
            .singleOrNull()
        return user != null
    }

    override suspend fun login(request: AuthRequest): User? = dbQuery {
        Users
            .select {
                Users.username.eq("${request.username}") and
                        Users.password.eq("${request.password}")
            }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id].value,
        name = row[Users.name],
        username = row[Users.username],
    )
}