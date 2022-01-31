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

import com.januszpol.models.Product
import com.januszpol.tables.ProductTable


fun Application.routeProduct() {
	getProduct()
	putProduct()
	postProduct()
	deleteProduct()
}


fun Application.getProduct() {
    
    routing {
    	get("/product") {
			var products = mutableListOf<Product>()
			transaction {
                products = ProductTable.selectAll().map { ProductTable.toProduct(it) }.toMutableList()
            }
            call.respond(Gson().toJson(products))
    	}

        get("/product/{id}") {
            val id: Int = call.parameters["id"]!!.toInt()
			var product = Product()
            transaction {
                product = ProductTable.select { ProductTable.id eq id }.map { ProductTable.toProduct(it) }.first()
            }
            call.respond(Gson().toJson(product))
        }

    }
}


private fun Application.putProduct() {
    routing {
        put("/product/{id}") {
            val params = call.receiveParameters()

			val p_id = call.parameters["id"]!!.toInt()
			val p_name = params["name"].toString()
			val p_description = params["description"].toString()
			val p_rating = params["rating"]!!.toInt()


            transaction {
                ProductTable.update({ ProductTable.id eq p_id }) {
					it[ProductTable.name] = 		p_name
					it[ProductTable.description] =	p_description
					it[ProductTable.rating] = 	 	p_rating
                }
            }
        }
    }
}


fun Application.postProduct() {

// curl -X POST http://127.0.0.1:8080/product -H "Content-Type: application/x-www-form-urlencoded" -d "login=Arek&email=sokolowski@uj.edu.pl&password=javaisthebest&realName='Arek Sokolowski'&age=45"

	routing {
		post("/product") {
			val params = call.receiveParameters()

			val p_name = params["name"].toString()
			val p_description = params["description"].toString()
			val p_rating = params["rating"]!!.toInt()
			
			transaction {
				ProductTable.insert {
					it[ProductTable.name] = 		p_name
					it[ProductTable.description] =	p_description
					it[ProductTable.rating] = 	 	p_rating
				}
			}

			call.respondText("Product stored correctly\n", status = HttpStatusCode.Created)
		}
	}
}


private fun Application.deleteProduct() {
    routing {
        delete("/product/{id}") {
            val id = call.parameters["id"]
            transaction {
                ProductTable.deleteWhere { ProductTable.id eq id!!.toInt() }
            }
        }
    }
}
