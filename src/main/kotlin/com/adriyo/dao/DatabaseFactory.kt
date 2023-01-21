package com.adriyo.dao

import com.adriyo.dao.tables.Notes
import com.adriyo.dao.tables.UserNotes
import com.adriyo.dao.tables.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:h2:file:./build/db;AUTO_SERVER=TRUE;"
            driverClassName = "org.h2.Driver"
            maximumPoolSize = 3
        }
        val dataSource = HikariDataSource(config)
        val database = Database.connect(dataSource)

        transaction(database) {
            SchemaUtils.create(Notes, Users, UserNotes)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    val daoNotes: DAONotes = DAONotesImpl()
    val daoUser: DAOUsers = DAOUsersImpl()
    val daoUserNotes: DAOUserNotes = DAOUserNotesImpl()
}