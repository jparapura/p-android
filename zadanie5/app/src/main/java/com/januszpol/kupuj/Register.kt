package com.januszpol.kupuj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent




class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun goToScreenOne(view: View) {
        val myIntent = Intent(view.context, MainActivity::class.java)
        startActivityForResult(myIntent, 0)
    }


}