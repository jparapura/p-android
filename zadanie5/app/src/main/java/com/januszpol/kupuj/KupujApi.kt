package com.januszpol.kupuj

import com.januszpol.kupuj.models.apimodels.ProductApi
import com.januszpol.kupuj.models.apimodels.UserApi
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KupujApi {

    @GET("user")
    fun getUsers(): Call<List<UserApi>>

    @GET("user/{id}")
    fun getUser(@Path("id") id: Int) : Call<UserApi>

    @GET("product")
    fun getProducts(): Call<List<ProductApi>>

    @POST("user")
    fun postUser(@Body user: UserApi) : Call<UserApi>

}