package com.zyyoona7.demo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.R

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/27.
 */
class MainAdapter:BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_main,null){


    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_item_data,"${helper.adapterPosition}. $item")
    }

}