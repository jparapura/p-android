package com.januszpol.models

data class Subscription(
	val id: Int? = null,
	val userId: Int,
	// TODO swap to Date
	val startDate: Int,
	val duration: Int
) {
	constructor() : this(0, 0, 0, 0)
}
