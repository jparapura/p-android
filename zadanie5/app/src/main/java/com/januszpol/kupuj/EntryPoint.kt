package com.januszpol.kupuj
import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import com.januszpol.kupuj.models.Product

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
    }

}
