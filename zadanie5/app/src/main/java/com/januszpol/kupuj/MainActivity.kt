package com.januszpol.kupuj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
//    private lateinit var loginView : TextView
//    private lateinit var passwordView : TextView

    private lateinit var loginFr : Fragment
    private lateinit var registerFr : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        loginView = findViewById<TextView>(R.id.loginTF)
//        passwordView = findViewById<TextView>(R.id.passwordTF)

        loginFr = LoginFragment()
        registerFr = RegisterFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, loginFr)
            commit()
        }

        //retrofitTest()
    }


    fun registerClicked(view: View) {
        //val myIntent = Intent(view.context, Register::class.java)
        //startActivityForResult(myIntent, 0)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, registerFr)
            addToBackStack(null)
            commit()
        }
    }

//    fun loginClicked(view: View) {
//        if (loginView.text.toString() == "aaa" && passwordView.text.toString() == "bba") {
//            val myIntent = Intent(view.context, Products::class.java)
//            startActivityForResult(myIntent, 0)
//        }
//        else {
//            loginView.text = ""
//            passwordView.text = ""
//        }
//    }
}