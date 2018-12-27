package com.zyyoona7.demo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class LinearSectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_section)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_linear_section)
        recyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = SectionAdapter()
        recyclerView.adapter = adapter

        val linearItemDecoration= RecyclerViewDivider.linear()
            .color(Color.RED)
            .dividerSize(dpToPx(0.5f))
            .marginStart(dpToPx(20f))
            .marginEnd(dpToPx(20f))
            .hideDividerForItemType(BaseQuickAdapter.HEADER_VIEW,BaseQuickAdapter.FOOTER_VIEW)
            .hideAroundDividerForItemType(adapter.sectionItemType())
            .hideLastDivider()
            .build()

        linearItemDecoration.addTo(recyclerView)
        adapter.setNewData(DataServer.createSectionLinearData(24))
    }
}
