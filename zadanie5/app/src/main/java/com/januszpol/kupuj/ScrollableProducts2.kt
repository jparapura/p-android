package com.januszpol.kupuj

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView

class ScrollableProducts2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollable_products2)


        val scrollView = ScrollView(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        scrollView.layoutParams = layoutParams

        val linearLayout = LinearLayout(this)
        val linearParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = linearParams

        scrollView.addView(linearLayout)

        val imageView1 = Button(this)
        val params1 =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        //setting margin
        params1.setMargins(0, 30, 0, 30)
        //aligning the layout to center of the screen
        params1.gravity = Gravity.CENTER
        imageView1.setLayoutParams(params1)
        //setting our own downloaded/custom image to the imageView
        imageView1.text = "SIEMA"
        imageView1.setBackgroundColor(Color.GREEN)
        linearLayout.addView(imageView1)
    }
}