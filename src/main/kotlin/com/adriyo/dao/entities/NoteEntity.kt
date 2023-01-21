package com.adriyo.dao.entities

import com.adriyo.dao.tables.Notes
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NoteEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<NoteEntity>(Notes)
    var title by Notes.title
    var description by Notes.description
}
