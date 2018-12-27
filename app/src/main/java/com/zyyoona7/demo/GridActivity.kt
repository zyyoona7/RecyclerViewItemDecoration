package com.zyyoona7.demo

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class GridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_grid)
        val gridLayoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        recyclerView.layoutManager= gridLayoutManager

        val adapter = SectionAdapter()
        recyclerView.adapter = adapter
//        adapter.setSpanSizeLookup { gridLayoutManager, position ->if(position%3==0) 2 else 1 }

        val linearItemDecoration= RecyclerViewDivider.grid()
            .color(Color.RED)
            .includeEdge()
            .includeStartEdge()
            .hideDividerForItemType(adapter.sectionItemType())
            .dividerSize(dpToPx(10f))
            .build()

        linearItemDecoration.addTo(recyclerView)
//        adapter.setNewData(DataServer.createGridData(20))
        adapter.setNewData(DataServer.createSectionGridData(20))
    }
}
