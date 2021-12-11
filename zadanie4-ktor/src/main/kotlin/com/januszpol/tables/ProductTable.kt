package com.januszpol.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*


import com.januszpol.models.Product


object ProductTable : Table("Products") {
	val id: Column<Int> = integer("id").autoIncrement()
	val name: Column<String> = varchar("name", 255)
	val description: Column<String> = varchar("description", 255)
	val rating: Column<Int> = integer("rating")

	override val primaryKey = PrimaryKey(id, name="PK_Product_ID")

	fun toProduct(row: ResultRow): Product =
		Product(
			id		   	 = row[ProductTable.id],
			name	   	 = row[ProductTable.name],
			description	 = row[ProductTable.description],
			rating	 	 = row[ProductTable.rating]
		)

}

