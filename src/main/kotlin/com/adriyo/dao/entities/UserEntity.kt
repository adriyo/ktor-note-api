package com.adriyo.dao.entities

import com.adriyo.dao.tables.UserNotes
import com.adriyo.dao.tables.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<UserEntity>(Users)
    val name by Users.id
    val username by Users.username
    val password by Users.password
    var notes by NoteEntity via UserNotes
}
