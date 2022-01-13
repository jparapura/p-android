package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.januszpol.kupuj.models.Product
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class ScrollableProducts : AppCompatActivity() {
    private lateinit var listView: ListView
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollable_products)

        val config = Realm.getDefaultConfiguration()
//        Realm.init(this)
//        val config = RealmConfiguration.Builder()
//            .name("localdb_v2")
//            .allowQueriesOnUiThread(true)
//            .allowWritesOnUiThread(true)
//            .compactOnLaunch()
//            .build()
        realm = Realm.getInstance(config)
        val task = realm.where(Product::class.java)
        val results = task.sort("id", Sort.ASCENDING).findAll()
        Log.v("EXAMPLE", "Lista wszystkiego:")
        for (i in 0 until results.size) {
            Log.v("EXAMPLE", "wypisuje ze scrollable products ${i} = ${results[i].toString()}")
        }

        listView = findViewById<ListView>(R.id.scrollableList)
// 1
        // UserApi(age=45, email=sokol@uj.edu.pl, id=2, login=Arek, password=javaisthebest, realName='Arek Sokolowski'), UserApi(age=51, email=szczypka@uj.edu.pl, id=4, login=szczypior, password=kolokolomogorow, realName=Edward Szczypka), UserApi(age=50, email=bakalarski@uj.edu.pl, id=5, login=bakalars, password=GRAFYYY, realName='SÅawomir Bakalarski')
        val recipeList = listOf("Fowler", "Beck", "Evans", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "W", "X", "Y", "Z")
// 2
        val listItems = arrayOfNulls<String>(results.size)
// 3
        for (i in 0 until results.size) {
            //val recipe = recipeList[i]
            listItems[i] = results[i]!!.name
        }
// 4
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            Log.v("EXAMPLE", "clicked on: ${position}")
            Log.v("EXAMPLE", "clicked on: ${results[position]!!.name}")
            // 1
            //val selectedRecipe = recipeList[position]

            //// 2
            //val detailIntent = RecipeDetailActivity.newIntent(context, selectedRecipe)

            //// 3
            val intent = Intent(this, ProductInfo::class.java)
            intent.putExtra("name",results[position]!!.name)
            intent.putExtra("description",results[position]!!.description)
            startActivity(intent)
        }
    }
}