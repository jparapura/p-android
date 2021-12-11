package com.januszpol.models

data class User(
	val id: Int? = null,
	val login: String,
	val email: String,
	val password: String,
	val realName: String,
	val age: Int
) {
	constructor() : this(0, "", "", "", "", 0)
}
