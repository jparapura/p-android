package com.januszpol.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*


import com.januszpol.models.User


object UserTable : Table("Users") {
	val id: Column<Int> = integer("id").autoIncrement()
	val login: Column<String> = varchar("login", 255)
	val email: Column<String> = varchar("email", 255)
	val password: Column<String> = varchar("password", 255)
	val realName: Column<String> = varchar("realName", 255)
	val age: Column<Int> = integer("age")

	override val primaryKey = PrimaryKey(id, name="PK_User_ID")

	fun toUser(row: ResultRow): User =
		User(
			id = row[UserTable.id],
			login = row[UserTable.login],
			email = row[UserTable.email],
			password = row[UserTable.password],
			realName = row[UserTable.realName],
			age = row[UserTable.age]
		)

}

