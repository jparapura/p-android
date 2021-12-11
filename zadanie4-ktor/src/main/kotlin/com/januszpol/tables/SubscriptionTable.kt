package com.januszpol.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*


import com.januszpol.models.Subscription


object SubscriptionTable : Table("Subscriptions") {
	val id: Column<Int> = integer("id").autoIncrement()
	val userId: Column<Int> = integer("userId")
	val startDate: Column<Int> = integer("startDate")
	val duration: Column<Int> = integer("duration")

	override val primaryKey = PrimaryKey(id, name="PK_Subscription_ID")

	fun toSubscription(row: ResultRow): Subscription =
		Subscription(
			id		   	 = row[SubscriptionTable.id],
			userId	   	 = row[SubscriptionTable.userId],
			startDate	 = row[SubscriptionTable.startDate],
			duration 	 = row[SubscriptionTable.duration]
		)

}

