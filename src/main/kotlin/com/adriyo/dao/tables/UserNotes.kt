package com.adriyo.dao.tables

import org.jetbrains.exposed.sql.Table

object UserNotes : Table() {
    val user = reference("user", Users)
    val note = reference("note", Notes)

    override val primaryKey = PrimaryKey(user, note, name = "PK_UserNotes_user_note")
}