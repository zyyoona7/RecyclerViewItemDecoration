package com.zyyoona7.demo.activity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.HorizontalDataAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class HorizontalStaggeredGridActivity : BaseActivity() {

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
    }

    override fun createAdapter(): RecyclerView.Adapter<BaseViewHolder> {
        return HorizontalDataAdapter()
    }

    override fun addHeaderFooter(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as HorizontalDataAdapter
        quickAdapter.addHeaderView(getView(R.layout.item_ver_header))
        quickAdapter.addFooterView(getView(R.layout.item_ver_footer))
    }

    override fun initItemDecoration(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.staggeredGrid()
            .spacingSize(dpToPx(10f))
            .includeEdge()
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as HorizontalDataAdapter
        val list = arrayListOf<String>()
        for (i in 0 until 5) {
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
