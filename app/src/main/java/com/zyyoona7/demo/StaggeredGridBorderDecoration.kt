package com.zyyoona7.demo

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

class StaggeredGridBorderDecoration(private val borderSize: Int,
                                    private val borderColor: ColorDrawable) :
    RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    super.getItemOffsets(outRect, view, parent, state)

    val lm = parent.layoutManager as StaggeredGridLayoutManager
    val lp = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

    val spanCount = lm.spanCount
    val spanIndex = lp.spanIndex
    val position = parent.getChildAdapterPosition(view)

    if (spanIndex == 0) {
      // left edge
      // should write left border
      outRect.left = borderSize
    }

    if (position < spanCount) {
      // first row
      // should write top border
      outRect.top = borderSize
    }

    outRect.right = borderSize
    outRect.bottom = borderSize
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    if (parent.childCount == 0) {
      return
    }
    0.rangeTo(parent.childCount - 1).forEach { index ->
      val child = parent.getChildAt(index)
      val lp = child.layoutParams as StaggeredGridLayoutManager.LayoutParams
      val spanIndex = lp.spanIndex

      val childTop = child.top - lp.topMargin
      val childBottom = child.bottom + lp.bottomMargin
      val childLeft = child.left
      val childRight = child.right

      if (spanIndex == 0) {
        // draw left horizontal border
        val leftBorderRight = childLeft - lp.leftMargin
        val leftBorderLeft = leftBorderRight - borderSize
        borderColor.setBounds(leftBorderLeft, childTop, leftBorderRight, childBottom + borderSize)
        borderColor.draw(c)
      }

      // draw right horizontal border
      val rightBorderLeft = childRight + lp.rightMargin
      val rightBorderRight = rightBorderLeft + borderSize
      borderColor.setBounds(rightBorderLeft, childTop, rightBorderRight, childBottom + borderSize)
      borderColor.draw(c)

      // draw bottom border
      val bottomBorderTop = childBottom
      val bottomBorderBottom = bottomBorderTop + borderSize
      borderColor.setBounds(childLeft, bottomBorderTop, childRight, bottomBorderBottom)
      borderColor.draw(c)
    }
  }
}