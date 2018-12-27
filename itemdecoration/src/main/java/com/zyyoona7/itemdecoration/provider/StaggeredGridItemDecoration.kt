package com.zyyoona7.itemdecoration.provider

import android.graphics.Rect
import android.support.annotation.Px
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.zyyoona7.itemdecoration.ext.itemCount

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/13.
 */
class StaggeredGridItemDecoration(builder: Builder) : RecyclerView.ItemDecoration() {

    private val spacingSize: Int = builder.spacingSize
    //orientation is Vertical,edge left right. Horizontal,edge top bottom
    private val isIncludeEdge: Boolean = builder.isIncludeEdge
    //orientation is Vertical,first row top. Horizontal,first column left
    private val isIncludeStartEdge: Boolean = builder.isIncludeStartEdge

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

        val layoutManager = parent.layoutManager as? StaggeredGridLayoutManager ?: return
        val params = view.layoutParams as? StaggeredGridLayoutManager.LayoutParams ?: return


        if (layoutManager.orientation == RecyclerView.VERTICAL) {
            //orientation vertical
            itemOffsetsVertical(outRect, itemPosition, layoutManager, params)
        } else {
            //orientation horizontal
            itemOffsetsHorizontal(outRect, itemPosition, layoutManager, params)
        }
    }

    private fun itemOffsetsVertical(
        outRect: Rect, itemPosition: Int, layoutManager: StaggeredGridLayoutManager,
        params: StaggeredGridLayoutManager.LayoutParams
    ) {

        val spanCount = layoutManager.spanCount
        val spanIndex = params.spanIndex
        val isFullSpan = params.isFullSpan
        if (isIncludeStartEdge) {
            if (spanIndex == itemPosition) {
                //first row top spacing
                outRect.top = spacingSize
            }
        }

        if (isIncludeEdge) {
            when {
                isFullSpan -> {
                    outRect.left = spacingSize
                    outRect.right = spacingSize
                }
                spanIndex == 0 -> {
                    //first column
                    outRect.left = spacingSize
                    outRect.right = spacingSize / 2
                }
                (spanIndex + 1) % spanCount == 0 -> {
                    //last column
                    outRect.right = spacingSize
                    outRect.left = spacingSize / 2
                }
                else -> {
                    outRect.right = spacingSize / 2
                    outRect.left = spacingSize / 2
                }
            }
        } else {
            when {
                isFullSpan -> {
                    outRect.left = 0
                    outRect.right = 0
                }
                spanIndex == 0 -> {
                    //first column
                    outRect.left = 0
                    outRect.right = spacingSize / 2
                }
                (spanIndex + 1) % spanCount == 0 -> {
                    //last column
                    outRect.right = 0
                    outRect.left = spacingSize / 2
                }
                else -> {
                    outRect.right = spacingSize / 2
                    outRect.left = spacingSize / 2
                }
            }
        }

        //无法确定最后一行
        outRect.bottom = spacingSize
    }

    private fun itemOffsetsHorizontal(
        outRect: Rect, itemPosition: Int,
        layoutManager: StaggeredGridLayoutManager, params: StaggeredGridLayoutManager.LayoutParams
    ) {

        val spanCount = layoutManager.spanCount
        val spanIndex = params.spanIndex
        val isFullSpan = params.isFullSpan
        if (isIncludeStartEdge) {
            if (spanIndex == itemPosition) {
                //first column left spacing
                outRect.left = spacingSize
            }
        }

        if (isIncludeEdge) {
            when {
                isFullSpan -> {
                    outRect.top = spacingSize
                    outRect.bottom = spacingSize
                }
                spanIndex == 0 -> {
                    //first row
                    outRect.top = spacingSize
                    outRect.bottom = spacingSize / 2
                }
                (spanIndex + 1) % spanCount == 0 -> {
                    //last row
                    outRect.top = spacingSize / 2
                    outRect.bottom = spacingSize
                }
                else -> {
                    outRect.top = spacingSize / 2
                    outRect.bottom = spacingSize / 2
                }
            }
        } else {
            when {
                isFullSpan -> {
                    outRect.top = 0
                    outRect.bottom = 0
                }
                spanIndex == 0 -> {
                    //first row
                    outRect.top = 0
                    outRect.bottom = spacingSize / 2
                }
                (spanIndex + 1) % spanCount == 0 -> {
                    //last row
                    outRect.top = spacingSize / 2
                    outRect.bottom = 0
                }
                else -> {
                    outRect.top = spacingSize / 2
                    outRect.bottom = spacingSize / 2
                }
            }
        }

        //无法确定最后一列
        outRect.right = spacingSize
    }

    class Builder {
        internal var spacingSize: Int = 0
        internal var isIncludeEdge: Boolean = false
        internal var isIncludeStartEdge: Boolean = false

        /**
         * orientation is Vertical,edge left right. Horizontal,edge top bottom
         */
        fun includeEdge() = apply { isIncludeEdge = true }

        /**
         * orientation is Vertical,first row top. Horizontal,first column left
         */
        fun includeStartEdge() = apply { isIncludeStartEdge = true }

        fun spacingSize(@Px size: Int) = apply { spacingSize = size }

        fun build() = StaggeredGridItemDecoration(this)
    }
}