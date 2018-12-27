package com.zyyoona7.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class StaggeredGridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staggered_grid)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_staggered_grid)
        val staggeredGridLayoutManager=StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = staggeredGridLayoutManager
        val adapter = DataAdapter()
        recyclerView.adapter = adapter

        val staggeredGridItemDecoration = RecyclerViewDivider.staggeredGrid()
            .spacingSize(dpToPx(10f))
            .includeEdge()
            .includeStartEdge()
//            .includeEndEdge()
            .build()

        staggeredGridItemDecoration.addTo(recyclerView)
        adapter.setNewData(createStaggerGridData())
//        adapter.setNewData(DataServer.createSectionStaggerGridData(25))
    }

   private fun createStaggerGridData():List<String>{
        val list= arrayListOf<String>()
        for (i in 0 until 5){
            list.add("ii")
            list.add("这是 StaggeredGridLayoutManager ItemDecoration 展示。这是 StaggeredGridLayoutManager ItemDecoration 展示。")
            list.add("这是 StaggeredGridLayoutManager ItemDecoration 展示。")
            list.add("StaggeredGridLayoutManager ItemDecoration 展示。")
            list.add("ItemDecoration 展示。")
            list.add("StaggeredGridLayoutManager")
            list.add("ItemDecoration")
        }
       return list
    }
}
