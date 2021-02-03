package com.dsl.util

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author dsl-abben
 * on 2020/02/19.
 */
object ViewHelpers {

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view  控件view
     * @param event 焦点位置
     */
    fun hideKeyboard(event: MotionEvent?, view: View?, activity: Activity) {
        try {
            if (view != null && event != null) {
                if (view is EditText) {
                    val location = IntArray(2)
                    view.getLocationInWindow(location)
                    val left = location[0]
                    val top = location[1]
                    val right = left + view.getWidth()
                    val bootom = top + view.getHeight()
                    // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                    if (event.rawX < left || event.rawX > right || event.y < top || event.rawY > bootom) {
                        // 隐藏键盘
                        val token = view.getWindowToken()
                        val inputMethodManager =
                            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(
                            token,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
