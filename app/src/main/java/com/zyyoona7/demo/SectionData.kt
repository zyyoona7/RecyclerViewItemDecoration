package com.zyyoona7.demo

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @author zyyoona7
 * @version v1.0
 * @since 2018/12/17.
 */
class SectionData : SectionEntity<SectionData> {

    var text: String? = null

    constructor(isHeader: Boolean, header: String) : super(isHeader, header) {}

    constructor(isHeader: Boolean, header: String, text: String) : super(isHeader, header) {
        this.text = text
    }
}
