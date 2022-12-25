package com.zyyoona7.demo.activity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.DataServer
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.HorizontalSectionAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class HorizontalSectionGridActivity : BaseActivity() {

    override fun createLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager {
        return androidx.recyclerview.widget.GridLayoutManager(
            this,
            2,
            androidx.recyclerview.widget.GridLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun createAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder> {
        return HorizontalSectionAdapter()
    }

    override fun addHeaderFooter(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>) {
        val sectionAdapter = adapter as HorizontalSectionAdapter
        sectionAdapter.addHeaderView(getView(R.layout.item_hor_header))
        sectionAdapter.addFooterView(getView(R.layout.item_hor_footer))
    }

    override fun initItemDecoration(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.grid()
            .asSpace()
            .dividerSize(dpToPx(10f))
            .hideDividerForItemType(BaseQuickAdapter.HEADER_VIEW, BaseQuickAdapter.FOOTER_VIEW)
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>) {
        val sectionAdapter = adapter as HorizontalSectionAdapter
        sectionAdapter.setNewData(DataServer.createSectionGridData(30))
    }

}
