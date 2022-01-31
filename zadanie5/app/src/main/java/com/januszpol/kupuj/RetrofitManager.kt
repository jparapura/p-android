package com.januszpol.kupuj

import android.util.Log
import com.januszpol.kupuj.models.Product
import com.januszpol.kupuj.models.apimodels.ProductApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    private val apiUrl: String = "http://2ae4-83-242-74-67.ngrok.io/"

    fun getProductsFromApi(realmApi: RealmApi) {
        Log.v("EXAMPLE", "building retrofit object")
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        Log.v("EXAMPLE", "successfully built retrofit object.")

        val service: KupujApi = retrofit.create(KupujApi::class.java)
        Log.v("EXAMPLE", "successfully created retrofit object.")

        val call = service.getProducts()
        call.enqueue(object : Callback<List<ProductApi>> {
            override fun onResponse(call: Call<List<ProductApi>>, response: Response<List<ProductApi>>) {
                if (response.code() == 200) {
                    val apiResponse = response.body()

                    val stringBuilder = apiResponse!![0].name

                    Log.v("EXAMPLE", "bangla: $stringBuilder")

                    for (i in apiResponse.indices) {
                        realmApi.postProductsToRealm(
                            Product(
                            apiResponse[i].id,
                            apiResponse[i].name,
                            apiResponse[i].description,
                            apiResponse[i].rating
                        )
                        )
                    }
                    realmApi.printAllProducts()
                }
            }

            override fun onFailure(call: Call<List<ProductApi>>, t: Throwable) {
                Log.v("EXAMPLE", "nie bangla: ${t.message}")
            }
        })
    }

    //fun getUsersFromApi() {
    //    Log.v("EXAMPLE", "building retrofit object")
    //    val retrofit = retrofit2.Retrofit.Builder()
    //        .baseUrl(apiUrl)
    //        .addConverterFactory(GsonConverterFactory.create())
    //        .client(OkHttpClient.Builder().build())
    //        .build()

    //    Log.v("EXAMPLE", "successfully built retrofit object.")

    //    val service: KupujApi = retrofit.create(KupujApi::class.java)
    //    Log.v("EXAMPLE", "successfully created retrofit object.")

    //    val call = service.getUsers()
    //    call.enqueue(object : Callback<List<UserApi>> {
    //        override fun onResponse(call: Call<List<UserApi>>, response: Response<List<UserApi>>) {
    //            if (response.code() == 200) {
    //                val apiResponse = response.body()

    //                Log.v("EXAMPLE", "bangla: ${apiResponse!!.joinToString()}")
    //            }
    //        }

    //        override fun onFailure(call: Call<List<UserApi>>, t: Throwable) {
    //            Log.v("EXAMPLE", "nie bangla: ${t.message}")
    //        }
    //    })

    //    Log.v("EXAMPLE", "przed call2")
    //    val call2 = service.getUser(2)
    //    Log.v("EXAMPLE", "po call2")
    //    call2.enqueue(object : Callback<UserApi> {
    //        override fun onResponse(call: Call<UserApi>, response: Response<UserApi>) {
    //            if (response.code() == 200) {
    //                val apiResponse = response.body()

    //                //val stringBuilder = apiResponse!![0].email

    //                //Log.v("EXAMPLE", "bangla: $stringBuilder")
    //                Log.v("EXAMPLE", "przed apiResponse")
    //                Log.v("EXAMPLE", "bangla: ${apiResponse.toString()}")
    //                Log.v("EXAMPLE", "po apiResponse")
    //            }
    //        }

    //        override fun onFailure(call: Call<UserApi>, t: Throwable) {
    //            Log.v("EXAMPLE", "nie bangla: ${t.message}")
    //        }
    //    })

    //Log.v("EXAMPLE", "przed postem")
    //val call3 = service.postUser(UserApi(38, "hryszko@ii.uj.edu.pl", 6, "jhryszko", "1234", "Jarosław Hryszko"))
    //Log.v("EXAMPLE", "po poście")

    //call3.enqueue(object : Callback<UserApi> {
    //    override fun onResponse(call: Call<UserApi>, response: Response<UserApi>) {
    //        if (response.code() == 200) {
    //            val apiResponse = response.body()

    //            //val stringBuilder = apiResponse!![0].email

    //            //Log.v("EXAMPLE", "bangla: $stringBuilder")
    //            Log.v("EXAMPLE", "przed apiResponse")
    //            Log.v("EXAMPLE", "bangla: ${apiResponse.toString()}")
    //            Log.v("EXAMPLE", "po apiResponse")
    //        }
    //    }

    //    override fun onFailure(call: Call<UserApi>, t: Throwable) {
    //        Log.v("EXAMPLE", "nie bangla: ${t.message}")
    //    }
    //})
    //}

}