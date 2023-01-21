package com.adriyo.dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val name = varchar("name", 50)
    val username = varchar("username", 25)
    val password = varchar("password", 20)
}