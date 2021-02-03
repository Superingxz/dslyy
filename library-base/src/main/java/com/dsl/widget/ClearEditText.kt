package com.dsl.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.dsl.base.R

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/1/29 11:40
 *     desc  : 一个简单的一键清除EditText
 * </pre>
 */
open class ClearEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle
) : AppCompatEditText(context!!, attrs, defStyle), OnFocusChangeListener, TextWatcher {
    private var mClearDrawable: Drawable? = null
    private var onClearClickListener: OnClearClickListener? = null

    interface OnClearClickListener {
        fun onClearClick()
    }

    fun setOnClearClickListener(onClearClickListener: OnClearClickListener?): ClearEditText {
        this.onClearClickListener = onClearClickListener
        return this
    }

    private fun init() {
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.icon_edit_delete,
                resources.newTheme()
            )
        }
        mClearDrawable?.setBounds(
            0,
            0,
            mClearDrawable!!.intrinsicWidth,
            mClearDrawable!!.intrinsicHeight
        )
        setClearIconVisible(false)
        onFocusChangeListener = this
        addTextChangedListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            if (event.action == MotionEvent.ACTION_UP) {
                val touchable = (
                    event.x > (
                        width -
                            paddingRight - mClearDrawable!!.intrinsicWidth
                        ) &&
                        event.x < width - paddingRight
                    )
                if (touchable) {
                    if (onClearClickListener != null) {
                        onClearClickListener!!.onClearClick()
                    }
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    open fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1], right, compoundDrawables[3]
        )
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
        setClearIconVisible(s.isNotEmpty())
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}
    fun setShakeAnimation() {
        this.animation = shakeAnimation(5)
    }

    companion object {
        fun shakeAnimation(counts: Int): Animation {
            val translateAnimation: Animation =
                TranslateAnimation(0f, 10f, 0f, 0f)
            translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
            translateAnimation.duration = 1000
            return translateAnimation
        }
    }

    init {
        init()
    }
}
