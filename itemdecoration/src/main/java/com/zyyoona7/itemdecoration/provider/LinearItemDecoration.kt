package com.zyyoona7.itemdecoration.provider

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.support.v4.util.ArraySet
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zyyoona7.itemdecoration.ext.itemCount
import com.zyyoona7.itemdecoration.ext.itemType

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */
class LinearItemDecoration internal constructor(builder: Builder) : RecyclerView.ItemDecoration() {

    private val isSpace: Boolean = builder.isSpace
    private val isHideLastDivider: Boolean = builder.isHideLastDivider
    private val divider: Drawable = builder.divider
    private val dividerSize: Int = builder.dividerSize
    private val marginStart: Int = builder.marginStart
    private val marginEnd: Int = builder.marginEnd
    private val hideDividerItemTypeSet: ArraySet<Int> = builder.hideDividerItemTypeSet
    private val hideAroundDividerItemTypeSet: ArraySet<Int> = builder.hideAroundDividerItemTypeSet

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

        val layoutManager = parent.layoutManager as? LinearLayoutManager ?: return

        val size = calculateDividerSize(layoutManager)

        if (layoutManager.orientation == RecyclerView.VERTICAL) {
            //LinearLayoutManager vertical
            if ((isHideLastDivider && isLastItem(itemPosition, itemCount))
                || nextIsHideItemType(itemPosition, itemCount, parent) || isHideItemType(itemPosition, parent)
            ) {
                outRect.setEmpty()
            } else {
                outRect.set(0, 0, 0, size)
            }
        } else {
            //LinearLayoutManager horizontal
            if ((isHideLastDivider && isLastItem(itemPosition, itemCount))
                || nextIsHideItemType(itemPosition, itemCount, parent) || isHideItemType(itemPosition, parent)
            ) {
                outRect.setEmpty()
            } else {
                outRect.set(0, 0, size, 0)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        val itemCount = parent.itemCount()
        if (isSpace || itemCount == 0) {
            return
        }

        val layoutManager = parent.layoutManager as? LinearLayoutManager ?: return

        if (layoutManager.orientation == RecyclerView.VERTICAL) {
            //LinearLayoutManager vertical
            drawVertical(c, parent, layoutManager, itemCount)
        } else {
            drawHorizontal(c, parent, layoutManager, itemCount)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView, layoutManager: LinearLayoutManager, itemCount: Int) {

        val left = parent.paddingLeft + marginStart
        val right = parent.width - parent.paddingRight - marginEnd
        val size = calculateDividerSize(layoutManager)

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val itemPosition = parent.getChildAdapterPosition(childView)
            if (itemPosition == RecyclerView.NO_POSITION) {
                return
            }

            if ((isHideLastDivider && isLastItem(itemPosition, itemCount))
                || nextIsHideItemType(itemPosition, itemCount, parent) || isHideItemType(itemPosition, parent)
            ) {
                continue
            }

            val params = childView.layoutParams as RecyclerView.LayoutParams
            val top = childView.bottom + params.bottomMargin
            val bottom = top + size

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView, layoutManager: LinearLayoutManager, itemCount: Int) {

        val top = parent.paddingTop + marginStart
        val bottom = parent.height - parent.paddingBottom - marginEnd
        val size = calculateDividerSize(layoutManager)

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val itemPosition = parent.getChildAdapterPosition(childView)
            if (itemPosition == RecyclerView.NO_POSITION) {
                return
            }

            if ((isHideLastDivider && isLastItem(itemPosition, itemCount))
                || nextIsHideItemType(itemPosition, itemCount, parent) || isHideItemType(itemPosition, parent)
            ) {
                continue
            }

            val params = childView.layoutParams as RecyclerView.LayoutParams
            val left = childView.right + params.rightMargin
            val right = left + size

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }

    /**
     * [itemPosition] is last item
     */
    private fun isLastItem(itemPosition: Int, itemCount: Int): Boolean {
        return itemCount > 0 && itemPosition == itemCount - 1
    }

    /**
     * [itemPosition]+1 is the hide divider itemType
     */
    private fun nextIsHideItemType(itemPosition: Int, itemCount: Int, parent: RecyclerView): Boolean {
        return if (itemPosition + 1 < itemCount) hideAroundDividerItemTypeSet.contains(parent.itemType(itemPosition + 1)) else false
    }

    /**
     * [itemPosition] is the hide divider itemType
     */
    private fun isHideItemType(itemPosition: Int, parent: RecyclerView): Boolean {
        val itemType = parent.itemType(itemPosition)
        return hideAroundDividerItemTypeSet.contains(itemType) || hideDividerItemTypeSet.contains(itemType)
    }

    /**
     * [divider] type calculate size
     */
    private fun calculateDividerSize(layoutManager: LinearLayoutManager): Int {
        return if (layoutManager.orientation == RecyclerView.VERTICAL) {
            if (divider is ColorDrawable) dividerSize else divider.intrinsicHeight
        } else {
            if (divider is ColorDrawable) dividerSize else divider.intrinsicWidth
        }
    }

    class Builder {

        internal var isSpace: Boolean = false
        internal var isHideLastDivider: Boolean = false
        internal var divider: Drawable = ColorDrawable(Color.TRANSPARENT)
        internal var dividerSize: Int = 0
        internal var marginStart: Int = 0
        internal var marginEnd: Int = 0
        internal val hideDividerItemTypeSet: ArraySet<Int> = ArraySet(1)
        internal val hideAroundDividerItemTypeSet: ArraySet<Int> = ArraySet(1)

        fun asSpace() = apply { isSpace = true }

        fun hideLastDivider() = apply { isHideLastDivider = true }

        fun color(@ColorInt color: Int) = apply { divider = ColorDrawable(color) }

        fun drawable(drawable: Drawable) = apply { divider = drawable }

        fun dividerSize(@Px size: Int) = apply { dividerSize = size }

        fun marginStart(@Px marginStart: Int) = apply { this.marginStart = marginStart }

        fun marginEnd(@Px marginEnd: Int) = apply { this.marginEnd = marginEnd }

        /**
         * hide divider of [itemTypes]
         */
        fun hideDividerForItemType(vararg itemTypes: Int) = apply {
            if (itemTypes.isNotEmpty()) {
                itemTypes.forEach { hideDividerItemTypeSet.add(it) }
            }
        }

        /**
         * hide current position divider and hide position-1 divider of [itemTypes]
         */
        fun hideAroundDividerForItemType(vararg itemTypes: Int) = apply {
            if (itemTypes.isNotEmpty()) {
                itemTypes.forEach { hideAroundDividerItemTypeSet.add(it) }
            }
        }

        fun build() = LinearItemDecoration(this)
    }
}