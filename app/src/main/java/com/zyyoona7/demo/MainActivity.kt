package com.zyyoona7.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zyyoona7.demo.activity.*
import com.zyyoona7.demo.adapter.MainAdapter
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainRv = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_main)

        mainRv.layoutManager =
            androidx.recyclerview.widget.StaggeredGridLayoutManager(
                2,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )
        val adapter = MainAdapter()
        mainRv.adapter = adapter

        RecyclerViewDivider.staggeredGrid()
            .includeEdge()
            .includeStartEdge()
            .spacingSize(dpToPx(10f))
            .build()
            .addTo(mainRv)

        adapter.setNewData(createData())

        adapter.setOnItemClickListener { adapter, view, position ->
            run {
                when (position) {
                    0 -> start(VerticalLinearActivity::class.java)
                    1 -> start(VerticalSectionLinearActivity::class.java)
                    2 -> start(HorizontalLinearActivity::class.java)
                    3 -> start(HorizontalSectionLinearActivity::class.java)
                    4 -> start(VerticalGridActivity::class.java)
                    5 -> start(VerticalSectionGridActivity::class.java)
                    6 -> start(HorizontalGridActivity::class.java)
                    7 -> start(HorizontalSectionGridActivity::class.java)
                    8 -> start(VerticalStaggeredGridActivity::class.java)
                    9 -> start(HorizontalStaggeredGridActivity::class.java)
                    else -> {
                        start(VerticalLinearActivity::class.java)
                    }
                }
            }
        }
    }

    private fun <T> start(actyClass: Class<T>) {
        startActivity(Intent(this, actyClass))
    }

    private fun createData(): List<String> {
        val list = arrayListOf<String>()
        list.add("Vertical Linear")
        list.add("Vertical Linear with section")
        list.add("Horizontal Linear")
        list.add("Horizontal Linear with section")
        list.add("Vertical Grid")
        list.add("Vertical Grid with section")
        list.add("Horizontal Grid")
        list.add("Horizontal Grid with section")
        list.add("Vertical Staggered Grid")
        list.add("Horizontal Staggered Grid")
        return list
    }
}
