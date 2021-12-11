package com.januszpol.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*


import com.januszpol.models.Order


object OrderTable : Table("Orders") {
	val id: Column<Int> = integer("id").autoIncrement()
	val userId: Column<Int> = integer("userId")
	val productId: Column<Int> = integer("productId")

	override val primaryKey = PrimaryKey(id, name="PK_Order_ID")

	fun toOrder(row: ResultRow): Order =
		Order(
			id		   	 = row[OrderTable.id],
			userId	   	 = row[OrderTable.userId],
			productId  	 = row[OrderTable.productId]
		)

}

