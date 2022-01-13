package com.januszpol.kupuj
import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import com.januszpol.kupuj.models.Product
import com.januszpol.kupuj.models.apimodels.ProductApi
import com.januszpol.kupuj.models.apimodels.UserApi
import io.realm.Sort
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EntryPoint : Application() {

    inline fun <reified T> T.TAG(): String = T::class.java.simpleName
    val apiUrl: String = "http://a1ac-2001-470-64f1-1-98c9-4d99-216d-dc5f.ngrok.io"
    lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG(), "odpalam się, witam")

        Realm.init(this)

        Log.v(TAG(), "initialized realm")

        val config = RealmConfiguration.Builder()
            .name("localdb_v2")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
            .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getInstance(config)
        Log.v("EXAMPLE", "Successfully opened a realm at: ${realm.path}")

        realm.executeTransaction { r: Realm ->
            r.deleteAll()
        }

        //printAllProducts()
        //getUsersFromApi()
        getProductsFromApi()
    }

    fun printAllProducts() {
        val task = realm.where(Product::class.java)
//        val result = task.equalTo("id", "0".toInt()).findFirst()
//        Log.v("EXAMPLE", "Fetched object by primary key: $result")
//        Log.v("EXAMPLE", "wyciągam sam tytuł: ${result!!.name}")
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkiego:")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "${i} = ${results[i].toString()}")
        }
    }

    fun getProductsFromApi() {
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
                    //Log.v("EXAMPLE", "bangla: ${apiResponse!!.joinToString()}")

                    // indicies works like 0 until apiResponse.size
                    realm.executeTransaction { r: Realm ->
                        for (i in apiResponse.indices) {
                            val movie = r.createObject(Product::class.java)
                            movie.id = apiResponse!![i].id
                            movie.name = apiResponse!![i].name
                            movie.description = apiResponse!![i].description
                            movie.rating = apiResponse!![i].rating
                        }
                    }
                    printAllProducts()
                }
            }

            override fun onFailure(call: Call<List<ProductApi>>, t: Throwable) {
                Log.v("EXAMPLE", "nie bangla: ${t.message}")
            }
        })
    }


//    fun getUsersFromApi() {
//        Log.v("EXAMPLE", "building retrofit object")
//        val retrofit = retrofit2.Retrofit.Builder()
//            .baseUrl(apiUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(OkHttpClient.Builder().build())
//            .build()
//
//        Log.v("EXAMPLE", "successfully built retrofit object.")
//
//        val service: KupujApi = retrofit.create(KupujApi::class.java)
//        Log.v("EXAMPLE", "successfully created retrofit object.")
//
//        val call = service.getUsers()
//        call.enqueue(object : Callback<List<UserApi>> {
//            override fun onResponse(call: Call<List<UserApi>>, response: Response<List<UserApi>>) {
//                if (response.code() == 200) {
//                    val apiResponse = response.body()
//
//                    //val stringBuilder = apiResponse!![0].email
//
//                    //Log.v("EXAMPLE", "bangla: $stringBuilder")
//                    Log.v("EXAMPLE", "bangla: ${apiResponse!!.joinToString()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<UserApi>>, t: Throwable) {
//                Log.v("EXAMPLE", "nie bangla: ${t.message}")
//            }
//        })
//        //print(service.getUsers())
//        //Log.v("EXAMPLE", "bangla: ${service.getUsers()}")
//    }

}
