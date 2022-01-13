package com.januszpol.kupuj.models.apimodels

data class UserApi(
    val age: Int,
    val email: String,
    val id: Int,
    val login: String,
    val password: String,
    val realName: String
)