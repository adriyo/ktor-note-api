package com.adriyo.dao.entities

import org.jetbrains.exposed.sql.Table

object Notes : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 120)
    val description = varchar("description", 1024)

    override val primaryKey = PrimaryKey(id)
}