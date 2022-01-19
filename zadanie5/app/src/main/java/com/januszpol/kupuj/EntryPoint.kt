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
import org.bson.types.ObjectId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class EntryPoint : Application() {

    inline fun <reified T> T.TAG(): String = T::class.java.simpleName
    val apiUrl: String = "http://64c3-89-75-70-253.ngrok.io"
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

        postProductsToRealm(Product(2, "Mad Max", "ścigałka na pustyni", 7))
        postProductsToRealm(Product(3, "Tron: Dziedzictwo", "nwm o co chodzi, ale Daft Punk zrobił kox muze", 5))
        printAllProducts()
        modifyProductWithIdFromRealm(2, Product(2, "Mad Max", "jadą w jedną stronę i wracają", 8))
        printAllProducts()
        val allProducts = getAllProductsFromRealm()
        for (i in 0 until allProducts.size) {
            Log.v("EXAMPLE", "produkty: ${i} = ${allProducts[i].name} ${allProducts[i].description}")
        }
        val productWithId = getProductWithIdFromRealm(2)
        Log.v("EXAMPLE", "produkt: = ${productWithId.name} ${productWithId.description}")
        deleteProductFromRealm(3)
        printAllProducts()
        //getUsersFromApi()
        //getProductsFromApi()
    }

    fun deleteProductFromRealm(id: Int) {
        realm.executeTransaction { r: Realm ->
            var result = r.where(Product::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.deleteFromRealm()
            result = null
        }
    }

    fun getProductWithIdFromRealm(id: Int): Product {
        val task = realm.where(Product::class.java)
            .equalTo("id", id)
            .findFirst()

        return Product(task!!.id, task!!.name, task!!.description, task!!.rating)
    }

    fun getAllProductsFromRealm(): List<Product> {
        val task = realm.where(Product::class.java).findAll()
        var result: List<Product> = emptyList()
        for (i in 0 until task.size) {
            result += Product(task[i]!!.id, task[i]!!.name, task[i]!!.description, task[i]!!.rating)
        }
        return result
    }

    fun modifyProductWithIdFromRealm(id: Int, target : Product) {
        realm.executeTransaction { r: Realm ->
            val result = r.where(Product::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.id = target.id
            result!!.name = target.name
            result!!.description = target.description
            result!!.rating = target.rating
        }
    }

    fun postProductsToRealm(p: Product) {
        realm.executeTransaction { r: Realm ->
            // Instantiate the class using the factory function.
            val product = r.createObject(Product::class.java)
            // Configure the instance.
            product.id = p.id
            product.name = p.name
            product.description = p.description
            product.rating = p.rating
        }
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
                    // TODO zamiast tego użyć postProductToRealm
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


    fun getUsersFromApi() {
        Log.v("EXAMPLE", "building retrofit object")
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        Log.v("EXAMPLE", "successfully built retrofit object.")

        val service: KupujApi = retrofit.create(KupujApi::class.java)
        Log.v("EXAMPLE", "successfully created retrofit object.")

        val call = service.getUsers()
        call.enqueue(object : Callback<List<UserApi>> {
            override fun onResponse(call: Call<List<UserApi>>, response: Response<List<UserApi>>) {
                if (response.code() == 200) {
                    val apiResponse = response.body()

                    //val stringBuilder = apiResponse!![0].email

                    //Log.v("EXAMPLE", "bangla: $stringBuilder")
                    Log.v("EXAMPLE", "bangla: ${apiResponse!!.joinToString()}")
                }
            }

            override fun onFailure(call: Call<List<UserApi>>, t: Throwable) {
                Log.v("EXAMPLE", "nie bangla: ${t.message}")
            }
        })

        Log.v("EXAMPLE", "przed call2")
        val call2 = service.getUser(2)
        Log.v("EXAMPLE", "po call2")
        call2.enqueue(object : Callback<UserApi> {
            override fun onResponse(call: Call<UserApi>, response: Response<UserApi>) {
                if (response.code() == 200) {
                    val apiResponse = response.body()

                    //val stringBuilder = apiResponse!![0].email

                    //Log.v("EXAMPLE", "bangla: $stringBuilder")
                    Log.v("EXAMPLE", "przed apiResponse")
                    Log.v("EXAMPLE", "bangla: ${apiResponse.toString()}")
                    Log.v("EXAMPLE", "po apiResponse")
                }
            }

            override fun onFailure(call: Call<UserApi>, t: Throwable) {
                Log.v("EXAMPLE", "nie bangla: ${t.message}")
            }
        })

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
    }

}
