package com.zyyoona7.demo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class LinearActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_linear)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter = DataAdapter()
        recyclerView.adapter = adapter

        val linearItemDecoration=RecyclerViewDivider.linear()
            .color(Color.RED)
            .dividerSize(dpToPx(0.5f))
            .marginStart(dpToPx(20f))
            .marginEnd(dpToPx(20f))
            .build()

        linearItemDecoration.addTo(recyclerView)
        adapter.setNewData(DataServer.createLinearData(2))

    }
}
