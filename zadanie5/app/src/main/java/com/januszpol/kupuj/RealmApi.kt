package com.januszpol.kupuj

import android.content.Context
import android.util.Log
import com.januszpol.kupuj.models.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class RealmApi(context: Context) {
    var realm: Realm

    // jakiś lepszy konstruktor
    init {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .name("localdb_v2")
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .compactOnLaunch()
            .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getInstance(config)
        Log.v("EXAMPLE", "Successfully opened a realm at: ${realm.path}")
    }

    fun deleteEverything() {
        realm.executeTransaction { r: Realm ->
            r.deleteAll()
        }
    }

    // ========== PRODUCT ==========

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
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkich porduktów")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "${i} = ${results[i].toString()}")
        }
    }


    // ========== USER ==========


    fun deleteUserFromRealm(id: Int) {
        realm.executeTransaction { r: Realm ->
            var result = r.where(User::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.deleteFromRealm()
            result = null
        }
    }


    fun getUserWithIdFromRealm(id: Int): User {
        val task = realm.where(User::class.java)
            .equalTo("id", id)
            .findFirst()

        return User(task!!.id, task!!.login, task!!.email, task!!.password, task!!.realName, task!!.age)
    }

    fun getAllUsersFromRealm(): List<User> {
        val task = realm.where(User::class.java).findAll()
        var result: List<User> = emptyList()
        for (i in 0 until task.size) {
            result += User(task[i]!!.id, task[i]!!.login, task[i]!!.email, task[i]!!.password, task[i]!!.realName, task[i]!!.age)
        }
        return result
    }

    fun modifyUserWithIdFromRealm(id: Int, target : User) {
        realm.executeTransaction { r: Realm ->
            val result = r.where(User::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.id = target.id
            result!!.login = target.login
            result!!.email = target.email
            result!!.password = target.password
            result!!.realName = target.realName
            result!!.age = target.age
        }
    }

    fun postUsersToRealm(p: User) {
        realm.executeTransaction { r: Realm ->
            // Instantiate the class using the factory function.
            val product = r.createObject(User::class.java)
            // Configure the instance.
            product.id = p.id
            product.login = p.login
            product.email = p.email
            product.password = p.password
            product.realName = p.realName
            product.age = p.age
        }
    }

    fun printAllUsers() {
        val task = realm.where(User::class.java)
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkich użytkowników")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "${i} = ${results[i].toString()}")
        }
    }



    // ========== SUBSCRIPTION ==========

    fun deleteSubscriptionFromRealm(id: Int) {
        realm.executeTransaction { r: Realm ->
            var result = r.where(Subscription::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.deleteFromRealm()
            result = null
        }
    }


    fun getSubscriptionWithIdFromRealm(id: Int): Subscription {
        val task = realm.where(Subscription::class.java)
            .equalTo("id", id)
            .findFirst()

        return Subscription(task!!.id, task!!.userId, task!!.startDate, task!!.duration)
    }

    fun getAllSubscriptionsFromRealm(): List<Subscription> {
        val task = realm.where(Subscription::class.java).findAll()
        var result: List<Subscription> = emptyList()
        for (i in 0 until task.size) {
            result += Subscription(task[i]!!.id, task[i]!!.userId, task[i]!!.startDate, task[i]!!.duration)
        }
        return result
    }

    fun modifySubscriptionWithIdFromRealm(id: Int, target : Subscription) {
        realm.executeTransaction { r: Realm ->
            val result = r.where(Subscription::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.id = target.id
            result!!.userId = target.userId
            result!!.startDate = target.startDate
            result!!.duration = target.duration
        }
    }

    fun postSubscriptionsToRealm(p: Subscription) {
        realm.executeTransaction { r: Realm ->
            // Instantiate the class using the factory function.
            val product = r.createObject(Subscription::class.java)
            // Configure the instance.
            product.id = p.id
            product.userId = p.userId
            product.startDate = p.startDate
            product.duration = p.duration
        }
    }

    fun printAllSubscriptions() {
        val task = realm.where(Subscription::class.java)
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkich subskrybcji")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "${i} = ${results[i].toString()}")
        }
    }




    // ========== ORDER ==========

    fun deleteOrderFromRealm(id: Int) {
        realm.executeTransaction { r: Realm ->
            var result = r.where(Order::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.deleteFromRealm()
            result = null
        }
    }


    fun getOrderWithIdFromRealm(id: Int): Order {
        val task = realm.where(Order::class.java)
            .equalTo("id", id)
            .findFirst()

        return Order(task!!.id, task!!.userId, task!!.productId)
    }

    fun getAllOrdersFromRealm(): List<Order> {
        val task = realm.where(Order::class.java).findAll()
        var result: List<Order> = emptyList()
        for (i in 0 until task.size) {
            result += Order(task[i]!!.id, task[i]!!.userId, task[i]!!.productId)
        }
        return result
    }

    fun modifyOrderWithIdFromRealm(id: Int, target : Order) {
        realm.executeTransaction { r: Realm ->
            val result = r.where(Order::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.id = target.id
            result!!.userId = target.userId
            result!!.productId = target.productId
        }
    }

    fun postOrdersToRealm(p: Order) {
        realm.executeTransaction { r: Realm ->
            // Instantiate the class using the factory function.
            val product = r.createObject(Order::class.java)
            // Configure the instance.
            product.id = p.id
            product.userId = p.userId
            product.productId = p.productId
        }
    }

    fun printAllOrders() {
        val task = realm.where(Order::class.java)
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkich zamówień")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "${i} = ${results[i].toString()}")
        }
    }




    // ========== CATEGORY ==========

    fun deleteCategoryFromRealm(id: Int) {
        realm.executeTransaction { r: Realm ->
            var result = r.where(Category::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.deleteFromRealm()
            result = null
        }
    }


    fun getCategoryWithIdFromRealm(id: Int): Category {
        val task = realm.where(Category::class.java)
            .equalTo("id", id)
            .findFirst()

        return Category(task!!.id, task!!.name)
    }

    fun getAllCategorysFromRealm(): List<Category> {
        val task = realm.where(Category::class.java).findAll()
        var result: List<Category> = emptyList()
        for (i in 0 until task.size) {
            result += Category(task[i]!!.id, task[i]!!.name)
        }
        return result
    }

    fun modifyCategoryWithIdFromRealm(id: Int, target : Category) {
        realm.executeTransaction { r: Realm ->
            val result = r.where(Category::class.java)
                .equalTo("id", id)
                .findFirst()

            result!!.id = target.id
            result!!.name = target.name
        }
    }

    fun postCategorysToRealm(p: Category) {
        realm.executeTransaction { r: Realm ->
            // Instantiate the class using the factory function.
            val product = r.createObject(Category::class.java)
            // Configure the instance.
            product.id = p.id
            product.name = p.name
        }
    }

    fun printAllCategorys() {
        val task = realm.where(Category::class.java)
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkich kategorii")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "${i} = ${results[i].toString()}")
        }
    }
}