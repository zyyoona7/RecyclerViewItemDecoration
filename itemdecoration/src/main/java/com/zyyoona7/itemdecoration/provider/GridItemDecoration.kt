package com.zyyoona7.itemdecoration.provider

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.support.v4.util.ArraySet
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zyyoona7.itemdecoration.ext.itemCount
import com.zyyoona7.itemdecoration.ext.itemType
import com.zyyoona7.itemdecoration.ext.spanIndex
import com.zyyoona7.itemdecoration.ext.spanSize

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */
class GridItemDecoration internal constructor(builder: Builder) : RecyclerView.ItemDecoration() {

    private val isSpace: Boolean = builder.isSpace
    private val divider: Drawable = builder.divider
    private val dividerSize: Int = builder.dividerSize
    private val isIncludeEdge: Boolean = builder.isIncludeEdge
    private val isIncludeStartEdge: Boolean = builder.isIncludeStartEdge
    //hide divider only work spanSize==spanCount itemType
    private val hideDividerItemTypeSet = builder.hideDividerItemTypeSet

    /**
     * add item decoration to [recyclerView]
     */
    fun addTo(recyclerView: RecyclerView) {
        removeFrom(recyclerView)
        recyclerView.addItemDecoration(this)
    }

    /**
     * remove item decoration from [recyclerView]
     */
    fun removeFrom(recyclerView: RecyclerView) {
        recyclerView.removeItemDecoration(this)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = parent.itemCount()
        if (itemCount == 0) {
            return
        }

        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return

        val spanCount = layoutManager.spanCount
        val spanIndex = layoutManager.spanIndex(itemPosition)
        val spanSize = layoutManager.spanSize(itemPosition)

        val verticalSize = calculateVerticalDividerSize()
        val horizontalSize = calculateHorizontalDividerSize()

        if (layoutManager.orientation == RecyclerView.VERTICAL) {
            if (spanIndex == 0 && isIncludeEdge) {
                //column 0
                outRect.left = horizontalSize
            }

            //spanIndex + spanSize equals spanCount last column
            if (spanIndex + spanSize == spanCount && !isIncludeEdge) {
                //last column and not include edge
                outRect.right = 0
            } else {
                outRect.right = horizontalSize
            }

            if (isIncludeStartEdge) {
                outRect.top = if (spanIndex == itemPosition) verticalSize else 0
            }

            if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                outRect.bottom = 0
            } else {
                outRect.bottom = verticalSize
            }
        } else {
            if (spanIndex == 0 && isIncludeEdge) {
                //column 0
                outRect.top = verticalSize
            }

            //spanIndex + spanSize equals spanCount last row
            if (spanIndex + spanSize == spanCount && !isIncludeEdge) {
                //last row and not include edge
                outRect.bottom = 0
            } else {
                outRect.bottom = verticalSize
            }

            if (isIncludeStartEdge) {
                outRect.left = if (spanIndex == itemPosition) verticalSize else 0
            }

            if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                outRect.right = 0
            } else {
                outRect.right = horizontalSize
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val itemCount = parent.itemCount()
        if (isSpace || itemCount == 0) {
            return
        }

        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return

        val spanCount = layoutManager.spanCount
        val verticalSize = calculateVerticalDividerSize()
        val horizontalSize = calculateHorizontalDividerSize()

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val itemPosition = parent.getChildAdapterPosition(childView)
            if (itemPosition == RecyclerView.NO_POSITION) {
                return
            }
            val spanIndex = layoutManager.spanIndex(itemPosition)
            val spanSize = layoutManager.spanSize(itemPosition)

            val params = childView.layoutParams as RecyclerView.LayoutParams

            val bLeft: Int = childView.left - params.leftMargin
            //bottom divider 补齐空缺位置，如果不加 item 对角间会有空隙
            var bRight = childView.right + params.rightMargin + horizontalSize
            val rTop = childView.top - params.topMargin
            val rBottom = childView.bottom + params.bottomMargin

            val bTop: Int = childView.bottom + params.bottomMargin
            val bBottom: Int = bTop + verticalSize
            val rLeft: Int = childView.right + params.rightMargin
            val rRight: Int = rLeft + horizontalSize

            val isDrawBottom: Boolean
            val isDrawRight: Boolean

            if (spanIndex + spanSize == spanCount && isIncludeEdge) {
                if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                    //span size full span count and hide divider,
                    isDrawBottom = false
                    isDrawRight = false
                } else {
                    //last column
                    //horizontal not draw bottom
                    isDrawBottom = layoutManager.orientation == RecyclerView.VERTICAL
                    //vertical not draw right
                    isDrawRight = layoutManager.orientation != RecyclerView.VERTICAL
                }
                //isIncludeEdge true last column 不补齐
                if (layoutManager.orientation == RecyclerView.VERTICAL) {
                    bRight = childView.right + params.rightMargin
                }
            } else if (spanIndex + spanSize == spanCount) {
                if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                    //span size full span count and hide divider,
                    isDrawBottom = false
                    isDrawRight = false
                } else {
                    //last column
                    //horizontal not draw bottom
                    isDrawBottom = layoutManager.orientation == RecyclerView.VERTICAL
                    //vertical not draw right
                    isDrawRight = layoutManager.orientation != RecyclerView.VERTICAL
                }
            } else {
                isDrawBottom = true
                isDrawRight = true
            }

            //draw bottom divider
            if (isDrawBottom) {
                divider.setBounds(bLeft, bTop, bRight, bBottom)
                divider.draw(c)
            }

            //draw right divider
            if (isDrawRight) {
                divider.setBounds(rLeft, rTop, rRight, rBottom)
                divider.draw(c)
            }
        }
    }

    /**
     * [divider] type calculate vertical size
     */
    private fun calculateVerticalDividerSize(): Int {
        return if (divider is ColorDrawable) dividerSize else divider.intrinsicHeight
    }

    /**
     * [divider] type calculate horizontal size
     */
    private fun calculateHorizontalDividerSize(): Int {
        return if (divider is ColorDrawable) dividerSize else divider.intrinsicWidth
    }

    /**
     * [itemPosition] is the hide end divider itemType
     */
    private fun isHideItemType(itemPosition: Int, parent: RecyclerView): Boolean {
        val itemType = parent.itemType(itemPosition)
        return hideDividerItemTypeSet.contains(itemType)
    }

    class Builder {

        internal var isSpace: Boolean = false
        internal var divider: Drawable = ColorDrawable(Color.TRANSPARENT)
        internal var dividerSize: Int = 0
        internal var isIncludeEdge: Boolean = false
        internal var isIncludeStartEdge: Boolean = false
        internal val hideDividerItemTypeSet: ArraySet<Int> = ArraySet(1)

        fun asSpace() = apply { isSpace = true }

        fun includeEdge() = apply { isIncludeEdge = true }

        fun includeStartEdge() = apply { isIncludeStartEdge = true }

        fun color(@ColorInt color: Int) = apply { divider = ColorDrawable(color) }

        fun drawable(drawable: Drawable) = apply { divider = drawable }

        fun dividerSize(@Px size: Int) = apply { dividerSize = size }

        /**
         * hide end divider of [itemTypes]
         */
        fun hideDividerForItemType(vararg itemTypes: Int) = apply {
            if (itemTypes.isNotEmpty()) {
                itemTypes.forEach { hideDividerItemTypeSet.add(it) }
            }
        }

        fun build() = GridItemDecoration(this)
    }
}