//package com.januszpol.plugins
//
//import io.ktor.routing.*
//import io.ktor.http.*
//import io.ktor.content.*
//import io.ktor.http.content.*
//import io.ktor.application.*
//import io.ktor.response.*
//import io.ktor.request.*
//
//import com.januszpol.models.Product
//
//
//private val products = listOf(
//	Product("Chleb", "Pyszny prosto od kurki"),
//	Product("Ziemniak", "Główny bohater opery podblokowej.")
//)
//
//
//fun Route.ruciki() {
//	get("/product/{id}") {
//		//call.respondText("sadasda")
//		val id: Int = call.parameters["id"]!!.toInt()
//		call.respondText(id.toString())
//	}
//
//}
//
//	get("/randomRabbit") {
//		call.respondText(products.random().toString())
//        call.respond(
//			HttpStatusCode.OK,
//			products.random()
//		)
//  }
//}
