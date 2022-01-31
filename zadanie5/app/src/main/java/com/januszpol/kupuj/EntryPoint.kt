package com.januszpol.kupuj
import android.app.Application
import android.util.Log
import com.januszpol.kupuj.models.Product
import com.januszpol.kupuj.models.User


class EntryPoint : Application() {

    inline fun <reified T> T.TAG(): String = T::class.java.simpleName
    lateinit var realmApi : RealmApi
    lateinit var retrofitManager: RetrofitManager

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG(), "onCreate started")

        realmApi = RealmApi(this)
        retrofitManager = RetrofitManager()

        Log.v(TAG(), "initialized realm")

        realmApi.deleteEverything()

        realmApi.postProductsToRealm(Product(2, "Mad Max: Na drodze gniewu", "ścigają się po pustyni", 7))
        realmApi.postProductsToRealm(Product(3, "Tron: Dziedzictwo", "nwm o co chodzi, ale Daft Punk zrobił kox muze", 5))
        realmApi.printAllProducts()
        realmApi.modifyProductWithIdFromRealm(2, Product(2, "Mad Max", "Max przyłącza się do grupy uciekinierek z Cytadeli - osady rządzonej przez Wiecznego Joe. Tyran wraz ze swoją bandą rusza za nimi w pościg.", 8))
        realmApi.printAllProducts()
        val allProducts = realmApi.getAllProductsFromRealm()
        for (i in 0 until allProducts.size) {
            Log.v("EXAMPLE", "produkty: ${i} = ${allProducts[i].name} ${allProducts[i].description}")
        }
        val productWithId = realmApi.getProductWithIdFromRealm(2)
        Log.v("EXAMPLE", "produkt: = ${productWithId.name} ${productWithId.description}")
        realmApi.deleteProductFromRealm(2)
        realmApi.deleteProductFromRealm(3)
        realmApi.printAllProducts()
        retrofitManager.getProductsFromApi(realmApi)

        realmApi.postUsersToRealm(User(0, "xd", "qwe@rty.com", "123", "XD", 1))
        realmApi.printAllUsers()
    }
}
