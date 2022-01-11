package com.januszpol.kupuj
import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import com.januszpol.kupuj.models.Product
import com.januszpol.kupuj.models.apimodels.UserApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EntryPoint : Application() {

    inline fun <reified T> T.TAG(): String = T::class.java.simpleName

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
        val realm = Realm.getInstance(config)
        Log.v("EXAMPLE", "Successfully opened a realm at: ${realm.path}")

        realm.executeTransaction { r: Realm ->
            // Instantiate the class using the factory function.
            val movie = r.createObject(Product::class.java)
            // Configure the instance.
            movie.id = 0
            movie.name = "GreatMovie"
            movie.description = "A white heterosexual male saves the day!"
            movie.rating = 9
        }

        Log.v("EXAMPLE", "Put object inside db.")

        val task = realm.where(Product::class.java)
        val result = task.equalTo("id", "0".toInt()).findFirst()
        Log.v("EXAMPLE", "Fetched object by primary key: $result")
        Log.v("EXAMPLE", "wyciągam sam tytuł: ${result!!.name}")


        retrofitTest()
    }


    fun retrofitTest() {
        Log.v("EXAMPLE", "building retrofit object")
        val retrofit = Retrofit.Builder()
            .baseUrl("http://1359-83-242-74-67.ngrok.io/")
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

                    val stringBuilder = apiResponse!![0].email

                    Log.v("EXAMPLE", "bangla: $stringBuilder")
                }
            }

            override fun onFailure(call: Call<List<UserApi>>, t: Throwable) {
                Log.v("EXAMPLE", "nie bangla: ${t.message}")
            }
        })
        //print(service.getUsers())
        //Log.v("EXAMPLE", "bangla: ${service.getUsers()}")
    }

}
