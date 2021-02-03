package com.dsl.widget

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author dsl-abben
 * on 2020/02/25.
 */
class DividerLine(private val orientation: Int, private val color: Int) :
    RecyclerView.ItemDecoration() {
    /**
     * 画笔
     */
    private val paint: Paint = Paint()

    /**
     * 分割线尺寸
     */
    private val size: Int

    /**
     * 分割线间距，垂直时候是分割线距离顶部和底部的高度
     */
    private val mSpace: Int = 0

    init {
        paint.color = 0xFFCCCCCC.toInt()
        size = 1
    }

    constructor() : this(0xFFCCCCCC.toInt(), LinearLayoutManager.VERTICAL)

    constructor(color: Int) : this(color, LinearLayoutManager.VERTICAL)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
        }
    }

    /**
     * 绘制水平分割线
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + size
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    /**
     * 绘制垂直分割线
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop + mSpace
        val bottom = parent.height - parent.paddingBottom - mSpace
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + size
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}
