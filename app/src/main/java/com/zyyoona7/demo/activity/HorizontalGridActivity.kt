package com.zyyoona7.demo.activity

import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.DataServer
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.HorizontalDataAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class HorizontalGridActivity : BaseActivity() {

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 2,GridLayoutManager.HORIZONTAL,false)
    }

    override fun createAdapter(): RecyclerView.Adapter<BaseViewHolder> {
        return HorizontalDataAdapter()
    }

    override fun addHeaderFooter(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as HorizontalDataAdapter
        quickAdapter.addHeaderView(getView(R.layout.item_hor_header))
//        quickAdapter.addFooterView(getView(R.layout.item_hor_footer))
    }

    override fun initItemDecoration(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.grid()
            .color(Color.BLUE)
            .dividerSize(dpToPx(10f))
            .hideLastDivider()
            .hideDividerForItemType(BaseQuickAdapter.HEADER_VIEW, BaseQuickAdapter.FOOTER_VIEW)
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val quickAdapter = adapter as HorizontalDataAdapter
        quickAdapter.setNewData(DataServer.createGridData(20))
    }

}
