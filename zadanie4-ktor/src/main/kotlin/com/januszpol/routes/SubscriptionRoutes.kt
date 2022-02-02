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

import com.januszpol.models.Subscription
import com.januszpol.tables.SubscriptionTable


fun Application.routeSubscription() {
	getSubscription()
	putSubscription()
	postSubscription()
	deleteSubscription()
}


fun Application.getSubscription() {
    
    routing {
    	get("/subscription") {
			var subscriptions = mutableListOf<Subscription>()
			transaction {
                subscriptions = SubscriptionTable.selectAll().map { SubscriptionTable.toSubscription(it) }.toMutableList()
            }
            call.respond(Gson().toJson(subscriptions))
    	}

        get("/subscription/{id}") {
            val id: Int = call.parameters["id"]!!.toInt()
			var subscription = Subscription()
            transaction {
                subscription = SubscriptionTable.select { SubscriptionTable.id eq id }.map { SubscriptionTable.toSubscription(it) }.first()
            }
            call.respond(Gson().toJson(subscription))
        }

    }
}


private fun Application.putSubscription() {
    routing {
        put("/subscription/{id}") {
            val params = call.receiveParameters()
			val p_id = call.parameters["id"]!!.toInt()
			val p_userId = params["userId"]!!.toInt()
			val p_startDate = params["startDate"]!!.toInt()
			val p_duration = params["duration"]!!.toInt()


            transaction {
                SubscriptionTable.update({ SubscriptionTable.id eq p_id }) {
					it[SubscriptionTable.userId] =  	p_userId
					it[SubscriptionTable.startDate] = 	p_startDate
					it[SubscriptionTable.duration] =	p_duration
                }
            }
        }
    }
}


fun Application.postSubscription() {

// curl -X POST http://127.0.0.1:8080/subscription -H "Content-Type: application/x-www-form-urlencoded" -d "login=Arek&email=sokolowski@uj.edu.pl&password=javaisthebest&realName='Arek Sokolowski'&age=45"

	routing {
		post("/subscription") {
            val params = call.receiveParameters()
			val p_userId = params["userId"]!!.toInt()
			val p_startDate = params["startDate"]!!.toInt()
			val p_duration = params["duration"]!!.toInt()

			transaction {
				SubscriptionTable.insert {
					it[SubscriptionTable.userId] =  	p_userId
					it[SubscriptionTable.startDate] = 	p_startDate
					it[SubscriptionTable.duration] =	p_duration
				}
			}

			call.respondText("Subscription stored correctly\n", status = HttpStatusCode.Created)
		}
	}
}


private fun Application.deleteSubscription() {
    routing {
        delete("/subscription/{id}") {
            val id = call.parameters["id"]
            transaction {
                SubscriptionTable.deleteWhere { SubscriptionTable.id eq id!!.toInt() }
            }
        }
    }
}
