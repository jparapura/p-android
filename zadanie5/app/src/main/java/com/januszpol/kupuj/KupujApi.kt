package com.januszpol.kupuj

import com.januszpol.kupuj.models.apimodels.ProductApi
import com.januszpol.kupuj.models.apimodels.UserApi
import retrofit2.Call
import retrofit2.http.GET

interface KupujApi {

    @GET("user")
    fun getUsers(): Call<List<UserApi>>

    @GET("product")
    fun getProducts(): Call<List<ProductApi>>
}