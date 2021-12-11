package com.januszpol.models

data class Category(
	val id: Int? = null,
	val name: String
) {
	constructor() : this(0, "")
}
