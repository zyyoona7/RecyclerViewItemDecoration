package com.zyyoona7.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearBtn = findViewById<AppCompatButton>(R.id.btn_linear)
        linearBtn.setOnClickListener {
            start(LinearActivity::class.java)
        }

        val linearSectionBtn = findViewById<AppCompatButton>(R.id.btn_linear_section)
        linearSectionBtn.setOnClickListener {
            start(LinearSectionActivity::class.java)
        }

        val gridBtn = findViewById<AppCompatButton>(R.id.btn_grid)
        gridBtn.setOnClickListener {
            start(GridActivity::class.java)
        }

        val staggeredGridBtn = findViewById<AppCompatButton>(R.id.btn_staggered)
        staggeredGridBtn.setOnClickListener {
            start(StaggeredGridActivity::class.java)
        }
    }

    private fun <T> start(actyClass: Class<T>) {
        startActivity(Intent(this, actyClass))
    }
}
