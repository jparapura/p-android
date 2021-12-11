package com.januszpol.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


import com.januszpol.models.*
import com.januszpol.tables.*
import com.januszpol.routes.*


fun Application.configureRouting() {
    
	Database.connect("jdbc:sqlite:janushop.db", "org.sqlite.JDBC")
	transaction {
		SchemaUtils.create(UserTable)
		SchemaUtils.create(ProductTable)
		SchemaUtils.create(OrderTable)
		SchemaUtils.create(CategoryTable)
		SchemaUtils.create(SubscriptionTable)
	}

    routing {
		routeUser()
		routeProduct()
		routeOrder()
		routeCategory()
		routeSubscription()
        get("/") {
        	call.respondText("Hello World!")
        }



//      // Static plugin. Try to access `/static/index.html`
//      static("/static") {
//          resources("static")
//      }
    }
}
