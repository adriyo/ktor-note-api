package com.adriyo.dao.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Notes : IntIdTable() {
    val title = varchar("title", 120)
    val description = varchar("description", 1024)
}