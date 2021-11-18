package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Products : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
    }

    fun aboutAppClicked(view: View) {
        val myIntent = Intent(view.context, About::class.java)
        startActivityForResult(myIntent, 0)
    }

    fun buyClicked(view: View) {
        val myIntent = Intent(view.context, ProductInfo::class.java)
        startActivityForResult(myIntent, 0)
    }
}