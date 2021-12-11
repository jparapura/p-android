package com.januszpol.models

data class Order(
	val id: Int? = null,
	val userId: Int,
	val productId: Int,
	// TODO date
) {
	constructor() : this(0, 0, 0)
}
