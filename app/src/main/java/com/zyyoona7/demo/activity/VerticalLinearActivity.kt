package com.zyyoona7.demo.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.DataServer
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.DataAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class VerticalLinearActivity : BaseActivity() {

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    override fun createAdapter(): RecyclerView.Adapter<BaseViewHolder> {
        return DataAdapter()
    }

    override fun addHeaderFooter(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as DataAdapter
        quickAdapter.addHeaderView(getView(R.layout.item_ver_header))
        quickAdapter.addFooterView(getView(R.layout.item_ver_footer))
    }

    override fun initItemDecoration(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.linear()
            .color(Color.RED)
            .dividerSize(dpToPx(1f))
            .marginStart(dpToPx(20f))
            .hideDividerForItemType(BaseQuickAdapter.HEADER_VIEW)
            .hideAroundDividerForItemType(BaseQuickAdapter.FOOTER_VIEW)
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as DataAdapter
        quickAdapter.setNewData(DataServer.createLinearData(20))
    }

}
