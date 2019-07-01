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
    //有无边界
    private val isIncludeEdge: Boolean = builder.isIncludeEdge
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

    /**
     * 计算空隙
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {

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
            //如果出现小数的话，可能会出现微小的偏差
            val realHorizontalSize =
                if (horizontalSize % spanCount == 0) horizontalSize else horizontalSize / spanCount * spanCount
            val eachItemHorDividerSize =
                if (spanSize == spanCount) (if (isIncludeEdge) 2 * realHorizontalSize else 0)
                else ((spanCount + if (isIncludeEdge) 1 else -1) * realHorizontalSize / spanCount)
            if (isIncludeEdge) {
                outRect.left =
                    (spanIndex + 1) * realHorizontalSize - spanIndex * eachItemHorDividerSize
                outRect.right = eachItemHorDividerSize - outRect.left
                outRect.top = if (spanIndex == itemPosition) verticalSize else 0
                if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                    outRect.bottom = 0
                } else {
                    outRect.bottom = verticalSize
                }
            } else {
                outRect.left = spanIndex * (realHorizontalSize - eachItemHorDividerSize)
                outRect.right = eachItemHorDividerSize - outRect.left
                outRect.top = 0
                if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                    outRect.bottom = 0
                } else {
                    outRect.bottom =
                        if (isInLastRowOrColumn(itemPosition, itemCount, spanCount, layoutManager))
                            0 else verticalSize
                }
            }

        } else {
            //如果出现小数的话，可能会出现微小的偏差
            val realVerticalSize =
                if (verticalSize % spanCount == 0) verticalSize else verticalSize / spanCount * spanCount
            val eachItemHorDividerSize =
                if (spanSize == spanCount) (if (isIncludeEdge) 2 * realVerticalSize else 0)
                else ((spanCount + if (isIncludeEdge) 1 else -1) * realVerticalSize / spanCount)
            if (isIncludeEdge) {
                outRect.top =
                    (spanIndex + 1) * realVerticalSize - spanIndex * eachItemHorDividerSize
                outRect.bottom = eachItemHorDividerSize - outRect.top
                outRect.left = if (spanIndex == itemPosition) horizontalSize else 0
                if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                    outRect.right = 0
                } else {
                    outRect.right = horizontalSize
                }
            } else {
                outRect.top = spanIndex * (realVerticalSize - eachItemHorDividerSize)
                outRect.bottom = eachItemHorDividerSize - outRect.top
                outRect.left = 0
                if (spanSize == spanCount && isHideItemType(itemPosition, parent)) {
                    outRect.right = 0
                } else {
                    outRect.right =
                        if (isInLastRowOrColumn(itemPosition, itemCount, spanCount, layoutManager))
                            0 else horizontalSize
                }
            }
        }
    }

    /**
     * 绘制，根据 item 绘制
     */
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
        val isVertical=layoutManager.orientation==GridLayoutManager.VERTICAL

        val realHorizontalSize = if (isVertical)
            (if (horizontalSize % spanCount == 0) horizontalSize else horizontalSize / spanCount * spanCount)
        else horizontalSize
        val realVerticalSize = if (!isVertical)
            (if (verticalSize % spanCount == 0) verticalSize else verticalSize / spanCount * spanCount)
        else verticalSize

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

            val bLeft: Int = childView.left - params.leftMargin -
                    (if (isIncludeEdge && spanIndex == 0 && isVertical) realHorizontalSize else 0)
            //bottom divider 补齐空缺位置，如果不加 item 对角间会有空隙
            //垂直方向时，底部 divider 的补位宽度
            val verticalBRight=if (!isIncludeEdge&&spanIndex+spanSize==spanCount) 0 else realHorizontalSize
            //水平方向时，底部 divider 的补位宽度
            val horizontalBRight=if (!isIncludeEdge&&isInLastRowOrColumn(itemPosition, itemCount, spanCount, layoutManager))
                0 else realHorizontalSize
            val bRight = childView.right + params.rightMargin +(if (isVertical) verticalBRight else horizontalBRight)
            val rTop = childView.top - params.topMargin -
                    (if (isIncludeEdge && spanIndex == 0 && !isVertical) realVerticalSize else 0)
            val rBottom = childView.bottom + params.bottomMargin

            val bTop: Int = childView.bottom + params.bottomMargin
            val bBottom: Int = bTop + realVerticalSize
            val rLeft: Int = childView.right + params.rightMargin
            val rRight: Int = rLeft + realHorizontalSize

            val isNotDrawEnd=spanIndex+spanSize==spanCount && !isIncludeEdge
            val isNotDrawLastItem=isInLastRowOrColumn(itemPosition, itemCount, spanCount, layoutManager)
                    && !isIncludeEdge
            val isNotDrawHideItem = spanSize == spanCount && isHideItemType(itemPosition, parent)
            //draw bottom divider
            if (!(isNotDrawHideItem && isVertical)
                && !(isNotDrawEnd && !isVertical)
                && !(isNotDrawLastItem && isVertical)) {
                divider.setBounds(bLeft, bTop, bRight, bBottom)
                divider.draw(c)
            }

            //draw right divider
            if (!(isNotDrawHideItem && !isVertical)
                && !(isNotDrawEnd && isVertical)
                && !(isNotDrawLastItem && !isVertical)) {
                divider.setBounds(rLeft, rTop, rRight, rBottom)
                divider.draw(c)
            }

            if (isVertical) {
                if (isIncludeEdge && spanIndex == 0) {
                    val lLeft = childView.left - params.leftMargin - realHorizontalSize
                    val lRight = childView.left - params.leftMargin
                    val lTop = childView.top - params.topMargin
                    val lBottom = childView.bottom + params.bottomMargin
                    divider.setBounds(lLeft, lTop, lRight, lBottom)
                    divider.draw(c)
                }
                if (isIncludeEdge && spanIndex == itemPosition) {
                    val tLeft = childView.left - params.leftMargin -
                            (if (spanIndex == 0) realHorizontalSize else 0)
                    val tRight = childView.right + params.rightMargin + realHorizontalSize
                    val tTop = childView.top - params.topMargin - realVerticalSize
                    val tBottom = childView.top - params.topMargin
                    divider.setBounds(tLeft, tTop, tRight, tBottom)
                    divider.draw(c)
                }
            } else {
                if (isIncludeEdge && spanIndex == 0) {
                    val tLeft = childView.left - params.leftMargin - realHorizontalSize
                    val tRight = childView.right + params.rightMargin + realHorizontalSize
                    val tTop = childView.top - params.topMargin - realVerticalSize
                    val tBottom = tTop + realVerticalSize
                    divider.setBounds(tLeft, tTop, tRight, tBottom)
                    divider.draw(c)
                }

                if (isIncludeEdge && spanIndex == itemPosition) {
                    val lLeft = childView.left - params.leftMargin - realHorizontalSize
                    val lRight = childView.left - params.leftMargin
                    val lTop = childView.top - params.topMargin
                    val lBottom = childView.bottom + params.bottomMargin+realVerticalSize

                    divider.setBounds(lLeft, lTop, lRight, lBottom)
                    divider.draw(c)
                }
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

    /**
     * [itemPosition] is in last row for Vertical or last column for Horizontal
     */
    private fun isInLastRowOrColumn(itemPosition: Int, itemCount: Int, spanCount: Int,
                                    layoutManager: GridLayoutManager): Boolean {
        val lastPosition = itemCount - 1
        if (itemPosition == lastPosition) {
            //最后一个 item 肯定在最后一行（列）
            return true
        }
        if (lastPosition - itemPosition < spanCount) {
            //有可能是最后一行的下标，候选
            //上一行（列）的最后一个 position
            var beforeLastItemPosition: Int = -1
            for (i in lastPosition - 1 downTo lastPosition - spanCount) {
                //从倒数第二个开始到候选 item 的最后一个，
                // 如果这中间有上一行（列）的最后一个，则这个 item 包括它之前的 item 都不在最后一行
                val spanIndex = layoutManager.spanIndex(i)
                val spanSize = layoutManager.spanSize(i)
                if (spanIndex + spanSize == spanCount) {
                    beforeLastItemPosition = i
                    break
                }
            }
            return itemPosition > beforeLastItemPosition
        }
        return false
    }

    class Builder {

        internal var isSpace: Boolean = false
        internal var divider: Drawable = ColorDrawable(Color.TRANSPARENT)
        internal var dividerSize: Int = 0
        internal var isIncludeEdge: Boolean = false
        internal val hideDividerItemTypeSet: ArraySet<Int> = ArraySet(1)

        fun asSpace() = apply { isSpace = true }

        fun includeEdge() = apply { isIncludeEdge = true }

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