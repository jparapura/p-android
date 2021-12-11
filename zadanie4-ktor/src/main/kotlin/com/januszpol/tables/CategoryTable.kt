package com.januszpol.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*


import com.januszpol.models.Category


object CategoryTable : Table("Categorys") {
	val id: Column<Int> = integer("id").autoIncrement()
	val name: Column<String> = varchar("name", 255)

	override val primaryKey = PrimaryKey(id, name="PK_Category_ID")

	fun toCategory(row: ResultRow): Category =
		Category(
			id		   	 = row[CategoryTable.id],
			name	   	 = row[CategoryTable.name],
		)

}

