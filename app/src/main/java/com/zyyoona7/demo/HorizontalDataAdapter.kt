package com.zyyoona7.demo

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */
class HorizontalDataAdapter() :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_horizontal_data,null) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_item_data,item)
    }
}