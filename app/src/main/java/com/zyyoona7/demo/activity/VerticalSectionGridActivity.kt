package com.zyyoona7.demo.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.DataServer
import com.zyyoona7.demo.R
import com.zyyoona7.demo.adapter.SectionAdapter
import com.zyyoona7.demo.dpToPx
import com.zyyoona7.itemdecoration.RecyclerViewDivider

class VerticalSectionGridActivity : BaseActivity() {

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this,2)
    }

    override fun createAdapter(): RecyclerView.Adapter<BaseViewHolder> {
        return SectionAdapter()
    }

    override fun addHeaderFooter(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val sectionAdapter=adapter as SectionAdapter
        sectionAdapter.addHeaderView(getView(R.layout.item_ver_header))
        sectionAdapter.addFooterView(getView(R.layout.item_ver_footer))
    }

    override fun initItemDecoration(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<BaseViewHolder>) {
        RecyclerViewDivider.grid()
            .asSpace()
            .dividerSize(dpToPx(10f))
            .hideDividerForItemType(BaseQuickAdapter.HEADER_VIEW,BaseQuickAdapter.FOOTER_VIEW)
            .build()
            .addTo(recyclerView)
    }

    override fun initData(adapter: RecyclerView.Adapter<BaseViewHolder>) {
        val sectionAdapter=adapter as SectionAdapter
        sectionAdapter.setNewData(DataServer.createSectionGridData(30))
    }

}
