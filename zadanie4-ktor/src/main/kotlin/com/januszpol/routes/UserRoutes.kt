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

import com.januszpol.models.User
import com.januszpol.tables.UserTable


fun Application.routeUser() {
	getUser()
	putUser()
	postUser()
	deleteUser()
}


fun Application.getUser() {
    
    routing {
    	get("/user") {
			var users = mutableListOf<User>()
			transaction {
                users = UserTable.selectAll().map { UserTable.toUser(it) }.toMutableList()
            }
            call.respond(Gson().toJson(users))
    	}

        get("/user/{id}") {
            val id: Int = call.parameters["id"]!!.toInt()
			var user = User()
            transaction {
                user = UserTable.select { UserTable.id eq id }.map { UserTable.toUser(it) }.first()
            }
            call.respond(Gson().toJson(user))
        }

    }
}


private fun Application.putUser() {
    routing {
        put("/user/{id}") {
            val params = call.receiveParameters()

			val p_id = call.parameters["id"]!!.toInt()
			val p_login = params["login"].toString()
			val p_email = params["email"].toString()
			val p_password = params["password"].toString()
			val p_realName = params["realName"].toString()
			val p_age = params["age"]!!.toInt()


            transaction {
                UserTable.update({ UserTable.id eq p_id }) {
					it[UserTable.login] = 		p_login
					it[UserTable.email] = 		p_email
					it[UserTable.password] = 	p_password
					it[UserTable.realName] = 	p_realName
					it[UserTable.age] = 		p_age
                }
            }
        }
    }
}


fun Application.postUser() {

// curl -X POST http://127.0.0.1:8080/user -H "Content-Type: application/x-www-form-urlencoded" -d "login=Arek&email=sokolowski@uj.edu.pl&password=javaisthebest&realName='Arek Sokolowski'&age=45"

	routing {
		post("/user") {
			val params = call.receiveParameters()

			val p_login = params["login"].toString()
			val p_email = params["email"].toString()
			val p_password = params["password"].toString()
			val p_realName = params["realName"].toString()
			val p_age = params["age"]!!.toInt()
			
			transaction {
				UserTable.insert {
					it[UserTable.login] = 		p_login
					it[UserTable.email] = 		p_email
					it[UserTable.password] = 	p_password
					it[UserTable.realName] = 	p_realName
					it[UserTable.age] = 		p_age
				}
			}

			call.respondText("User stored correctly\n", status = HttpStatusCode.Created)
		}
	}
}


private fun Application.deleteUser() {
    routing {
        delete("/user/{id}") {
            val id = call.parameters["id"]
            transaction {
                UserTable.deleteWhere { UserTable.id eq id!!.toInt() }
            }
        }
    }
}
