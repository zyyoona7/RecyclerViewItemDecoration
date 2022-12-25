package com.zyyoona7.demo.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.DataAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class VerticalStaggeredGridActivity : BaseActivity() {

    override fun createLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager {
        return androidx.recyclerview.widget.StaggeredGridLayoutManager(
            2,
            androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun createAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder> {
        return DataAdapter()
    }

    override fun addHeaderFooter(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as DataAdapter
        quickAdapter.addHeaderView(getView(R.layout.item_ver_header))
        quickAdapter.addFooterView(getView(R.layout.item_ver_footer))
    }

    override fun initItemDecoration(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.staggeredGrid()
            .spacingSize(dpToPx(10f))
            .includeEdge()
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as DataAdapter
        val list= arrayListOf<String>()
        for (i in 0 until 5){
            list.add("vertical")
            list.add("vertical staggered grid")
            list.add("vertical staggered")
            list.add("vertical staggered grid layout manager show. woo~~~")
            list.add("vertical staggered grid layout manager")
            list.add("vertical staggered grid layout manager show.")
        }
        quickAdapter.setNewData(list)
    }

}
