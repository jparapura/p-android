package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun goBackClicked(view: View) {
        val myIntent = Intent(view.context, ScrollableProducts::class.java)
        startActivityForResult(myIntent, 0)
    }
}