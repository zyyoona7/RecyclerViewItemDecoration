package com.zyyoona7.demo

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */

fun dpToPx(dp:Float)=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,Resources.getSystem().displayMetrics).toInt()