package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class ProductInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        Log.v("EXAMPLE", "wypisuje z ProductInfo: ${name} ${description}")
        val nameView = findViewById<TextView>(R.id.productName)
        val descView = findViewById<TextView>(R.id.productDescription)
        nameView.text = name
        descView.text = description
    }

    fun buyClicked(view: View) {
        val myIntent = Intent(view.context, Products::class.java)
        startActivityForResult(myIntent, 0)
    }
}