package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var loginView : TextView
    private lateinit var passwordView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginView = findViewById<TextView>(R.id.loginTF)
        passwordView = findViewById<TextView>(R.id.passwordTF)
    }

    fun registerClicked(view: View) {
        val myIntent = Intent(view.context, Register::class.java)
        startActivityForResult(myIntent, 0)
    }

    fun loginClicked(view: View) {
        if (loginView.text.toString() == "aaa" && passwordView.text.toString() == "bba") {
            val myIntent = Intent(view.context, Products::class.java)
            startActivityForResult(myIntent, 0)
        }
        else {
            loginView.text = ""
            passwordView.text = ""
        }
    }
}