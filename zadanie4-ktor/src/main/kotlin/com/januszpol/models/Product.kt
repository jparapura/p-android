package com.januszpol.models
//import kotlinx.serialization.Serializable

//@Serializable
data class Product (
	val id: Int? = null,
	val name: String,
	val description: String,
	val rating: Int,
	// TODO
	//val thumbnail: Image
) {
	constructor() : this(0, "", "", 0)
}
