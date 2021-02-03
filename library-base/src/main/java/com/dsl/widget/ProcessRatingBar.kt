package com.dsl.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.dsl.base.R

/**
 * 展示评价分数控件
 *
 * @author dsl-abben
 * on 2020/01/08.
 */
class ProcessRatingBar : View {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * 正常、选中的星星
     */
    private var mStarNormal: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.icon_start_normal)
    private var mStarSelected: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.icon_start_selected)

    /**
     * 星星的总数
     */
    private var mStartTotalNumber = 5

    /**
     * 选中的星星个数
     */
    private var mSelectedNumber = 0

    /**
     * 星星之间的间距
     */
    private var mStartDistance = 2

    /**
     * 画笔
     */
    private val mPaint = Paint()

    /**
     * 设置选中星星的数量
     */
    fun setSelectedNumber(selectedNumber: Int) {
        if (selectedNumber in 0..mStartTotalNumber) {
            this.mSelectedNumber = selectedNumber
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 用正常的一个星星图片去测量高
        val height = paddingTop + paddingBottom + mStarNormal.height
        // 宽 = 星星的宽度*总数 + 星星的间距*（总数-1） +padding
        val width =
            paddingLeft + paddingRight + mStarNormal.width * mStartTotalNumber + mStartDistance * (mStartTotalNumber - 1)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        // 循环绘制
        for (i in 0 until mStartTotalNumber) {
            var left = paddingLeft
            // 从第二个星星开始，给它设置星星的间距
            if (i > 0) {
                left = paddingLeft + i * (mStarNormal.width + mStartDistance)
            }
            val top = paddingTop
            // 绘制选中的星星
            if (i < mSelectedNumber) {
                // 比当前选中的数量小
                if (i < mSelectedNumber - 1) {
                    canvas?.drawBitmap(mStarSelected, left.toFloat(), top.toFloat(), mPaint)
                } else {
                    canvas?.drawBitmap(mStarSelected, left.toFloat(), top.toFloat(), mPaint)
                }
            } else {
                // 绘制正常的星星
                canvas?.drawBitmap(mStarNormal, left.toFloat(), top.toFloat(), mPaint)
            }
        }
    }
}
