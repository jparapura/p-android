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

import com.januszpol.models.Category
import com.januszpol.tables.CategoryTable


fun Application.routeCategory() {
	getCategory()
	putCategory()
	postCategory()
	deleteCategory()
}


fun Application.getCategory() {
    
    routing {
    	get("/category") {
			var categories = mutableListOf<Category>()
			transaction {
                categories = CategoryTable.selectAll().map { CategoryTable.toCategory(it) }.toMutableList()
            }
            call.respond(Gson().toJson(categories))
    	}

        get("/category/{id}") {
            val id: Int = call.parameters["id"]!!.toInt()
			var category = Category()
            transaction {
                category = CategoryTable.select { CategoryTable.id eq id }.map { CategoryTable.toCategory(it) }.first()
            }
            call.respond(Gson().toJson(category))
        }

    }
}


private fun Application.putCategory() {
    routing {
        // aktualizacja całego rekordu
        put("/category/{id}") {
            val params = call.receiveParameters()

			val p_id = call.parameters["id"]!!.toInt()
			val p_name = params["name"].toString()

            transaction {
                CategoryTable.update({ CategoryTable.id eq p_id }) {
					it[CategoryTable.name] =		p_name
                }
            }


        }
    }
}


fun Application.postCategory() {
	routing {
        // nowy rekord
		post("/category") {
            val params = call.receiveParameters()

            val p_name = params["name"].toString()

			transaction {
				CategoryTable.insert {
					it[CategoryTable.name] =		p_name
				}
			}

			call.respondText("Category stored correctly\n", status = HttpStatusCode.Created)
		}
	}
}


private fun Application.deleteCategory() {
    routing {
        delete("/category/{id}") {
            val id = call.parameters["id"]
            transaction {
                CategoryTable.deleteWhere { CategoryTable.id eq id!!.toInt() }
            }
        }
    }
}
