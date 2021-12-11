package com.januszpol.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

import com.januszpol.models.Product

// TODO zmienić ip, bo nie będzie działac w emulatorze androida
//private const val BASE_URL = "http://localhost:8080"

private val products = listOf(
	Product("Krzystof", "Przystalski"),
	Product("Karol", "Przystalski")
)


fun Route.ruciki() {
//	route("/customer") {
//        get("{id}") {
//            val id: Int = call.parameters["id"]!!.toInt()
//			call.respondText(id.toString())
//        }
//    }

	//routing {
	// TODO post
	get("/product/{id}") {
		//call.respondText("sadasda")
		val id: Int = call.parameters["id"]!!.toInt()
		call.respondText("Krzysztof" + id.toString())
		//call.respond(
		//	HttpStatusCode.OK,
		//)
	}
	//}

}

//	get("/randomRabbit") {
//		call.respondText(products.random().toString())
//        call.respond(
//			HttpStatusCode.OK,
//			products.random()
//		)
//  }
//}
