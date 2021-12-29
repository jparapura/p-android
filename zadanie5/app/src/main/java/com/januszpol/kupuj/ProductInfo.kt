package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ProductInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
    }

    fun buyClicked(view: View) {
        val myIntent = Intent(view.context, Products::class.java)
        startActivityForResult(myIntent, 0)
    }
}