package com.dsl.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.ScrollView
import com.blankj.utilcode.util.KeyboardUtils
import com.dsl.base.R
import com.dsl.base.databinding.LayoutTitleBinding

/**
 * <pre>
 *     author: MoYaoZhi
 *     email : 1803117759@qq.com
 *     time  : 2021/1/25
 *     desc  : 标题控件
 * </pre>
 */
class TitleLayout : RelativeLayout {
    constructor(context: Context) : super(context, null)

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        mContext = context
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout)
        mTitle = a.getString(R.styleable.TitleLayout_title)
        mShowLine = a.getBoolean(R.styleable.TitleLayout_showLine, false)
        a.recycle()
        init()
        mLayoutTitleBinding.titleTitleTv.text = mTitle
        if (!mShowLine) {
            mLayoutTitleBinding.vLine.visibility = View.GONE
        } else {
            mLayoutTitleBinding.vLine.visibility = View.VISIBLE
        }
    }

    private var listener: TitleListener? = null
    private lateinit var mLayoutTitleBinding: LayoutTitleBinding
    private var mContext: Context? = null
    private var mTitle: String? = null
    private var mShowLine = false

    private fun init() {
        mLayoutTitleBinding = LayoutTitleBinding.inflate(LayoutInflater.from(mContext), this, true)
        mLayoutTitleBinding.titleBackImgV.setOnClickListener {
            if (listener != null) {
                listener!!.onClickBack()
            } else {
                val context1 = context
                if (context1 != null && context1 is Activity) {
                    KeyboardUtils.hideSoftInput(context1)
                    context1.finish()
                }
            }
        }
        mLayoutTitleBinding.llTitleRight1.setOnClickListener {
            if (listener != null) {
                listener!!.onClickRight1()
            }
        }
        mLayoutTitleBinding.llTitleRight2.setOnClickListener {
            if (listener != null) {
                listener!!.onClickRight2()
            }
        }
    }

    fun setBack(activity: Activity) {
        mLayoutTitleBinding.titleBackImgV.setOnClickListener { activity.finish() }
    }

    fun hideLeft() {
        mLayoutTitleBinding.titleBackImgV.visibility = View.GONE
    }

    fun hideLine() {
        mLayoutTitleBinding.vLine.visibility = View.GONE
    }

    fun showLine() {
        mLayoutTitleBinding.vLine.visibility = View.VISIBLE
    }

    fun setTitleText(title: String?) {
        mLayoutTitleBinding.titleTitleTv.text = title
    }

    fun setTitleText(resId: Int) {
        setTitleText(resources.getString(resId))
    }

    fun setRightTextColor(color: Int) {
        mLayoutTitleBinding.tvTitleRight1.setTextColor(color)
    }

    fun setRight1Style(resId: Int, text: String?) {
        if (resId != 0 && TextUtils.isEmpty(text)) {
            mLayoutTitleBinding.ivTitleRight1.visibility = View.VISIBLE
            mLayoutTitleBinding.ivTitleRight1.setImageResource(resId)
            mLayoutTitleBinding.tvTitleRight1.visibility = View.GONE
        } else {
            mLayoutTitleBinding.tvTitleRight1.visibility = View.VISIBLE
            mLayoutTitleBinding.tvTitleRight1.text = text
            mLayoutTitleBinding.ivTitleRight1.visibility = View.GONE
        }
    }

    fun setRight2Style(resId: Int, text: String?) {
        if (resId != 0 && TextUtils.isEmpty(text)) {
            mLayoutTitleBinding.ivTitleRight2.visibility = View.VISIBLE
            mLayoutTitleBinding.ivTitleRight2.setImageResource(resId)
            mLayoutTitleBinding.tvTitleRight2.visibility = View.GONE
        } else {
            mLayoutTitleBinding.tvTitleRight2.visibility = View.VISIBLE
            mLayoutTitleBinding.tvTitleRight2.text = text
            mLayoutTitleBinding.ivTitleRight2.visibility = View.GONE
        }
    }

    /**
     * 设置标题布局各组件点击事件
     *
     * @param listener
     */
    fun setOnTitleListener(listener: TitleListener?) {
        this.listener = listener
    }

    interface TitleListener {
        /**
         * 左侧Back按钮点击事件
         */
        fun onClickBack()

        /**
         * 右侧第一个按钮点击事件
         */
        fun onClickRight1()

        /**
         * 右侧第二个按钮点击事件
         */
        fun onClickRight2()
    }

    abstract class TitleBack : TitleListener {
        override fun onClickRight1() {}
        override fun onClickRight2() {}
    }

    fun startStyle_A() {
        mLayoutTitleBinding.root.setBackgroundColor(Color.parseColor("#00ffffff"))
        mLayoutTitleBinding.backicon.setBackgroundResource(R.drawable.ic_back_white)
        mLayoutTitleBinding.titleTitleTv.visibility = View.GONE
    }

    fun startStyle_B() {
        mLayoutTitleBinding.root.setBackgroundColor(Color.parseColor("#ffffff"))
        mLayoutTitleBinding.backicon.setBackgroundResource(R.drawable.ic_back_black)
        mLayoutTitleBinding.titleTitleTv.visibility = View.VISIBLE
    }

    fun startStyle_C() {
        mLayoutTitleBinding.root.setBackgroundColor(Color.parseColor("#00ffffff"))
        mLayoutTitleBinding.backicon.setBackgroundResource(R.drawable.ic_back_white)
        mLayoutTitleBinding.titleTitleTv.setTextColor(Color.parseColor("#ffffff"))
    }

    fun setScrollViewLis(scrollView: ScrollView) {
        mLayoutTitleBinding.topbar.visibility = View.VISIBLE
        startStyle_A()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener { view: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                if (scrollY > 0) {
                    startStyle_B()
                } else {
                    startStyle_A()
                }
            }
        }
    }
}
