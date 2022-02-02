package com.januszpol.routes

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

import com.google.gson.Gson

import com.januszpol.models.Order
import com.januszpol.tables.OrderTable


fun Application.routeOrder() {
	getOrder()
	putOrder()
	postOrder()
	deleteOrder()
}


fun Application.getOrder() {
    
    routing {
    	get("/order") {
			var orders = mutableListOf<Order>()
			transaction {
                orders = OrderTable.selectAll().map { OrderTable.toOrder(it) }.toMutableList()
            }
            call.respond(Gson().toJson(orders))
    	}

        get("/order/{id}") {
            val id: Int = call.parameters["id"]!!.toInt()
			var order = Order()
            transaction {
                order = OrderTable.select { OrderTable.id eq id }.map { OrderTable.toOrder(it) }.first()
            }
            call.respond(Gson().toJson(order))
        }

    }
}


private fun Application.putOrder() {
    routing {
        put("/order/{id}") {
            val params = call.receiveParameters()
			val p_id = call.parameters["id"]!!.toInt()
			val p_userId = params["userId"]!!.toInt()
			val p_productId = params["productId"]!!.toInt()


            transaction {
                OrderTable.update({ OrderTable.id eq p_id }) {
					it[OrderTable.userId] =		p_userId
					it[OrderTable.productId] = 	p_productId
                }
            }
        }
    }
}


fun Application.postOrder() {

	routing {
		post("/order") {
            val params = call.receiveParameters()
			val p_userId = params["userId"]!!.toInt()
			val p_productId = params["productId"]!!.toInt()
			
			transaction {
				OrderTable.insert {
					it[OrderTable.userId] =		p_userId
					it[OrderTable.productId] = 	p_productId
				}
			}

			call.respondText("Order stored correctly\n", status = HttpStatusCode.Created)
		}
	}
}


private fun Application.deleteOrder() {
    routing {
        delete("/order/{id}") {
            val id = call.parameters["id"]
            transaction {
                OrderTable.deleteWhere { OrderTable.id eq id!!.toInt() }
            }
        }
    }
}
