package com.zyyoona7.demo.activity

import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.DataServer
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.DataAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class VerticalGridActivity : BaseActivity() {

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 4)
    }

    override fun createAdapter(): RecyclerView.Adapter<BaseViewHolder> {
        return DataAdapter()
    }

    override fun addHeaderFooter(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as DataAdapter
        quickAdapter.addHeaderView(getView(R.layout.item_ver_header))
//        quickAdapter.addFooterView(getView(R.layout.item_ver_footer))
    }

    override fun initItemDecoration(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.grid()
            .color(Color.BLUE)
            .dividerSize(dpToPx(10f))
//            .includeEdge()
//            .hideLastDivider()
            .hideDividerForItemType(BaseQuickAdapter.HEADER_VIEW, BaseQuickAdapter.FOOTER_VIEW)
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as DataAdapter
//        quickAdapter.setSpanSizeLookup { gridLayoutManager, position ->
//            2 }
        quickAdapter.setNewData(DataServer.createGridData(21))
    }

}
