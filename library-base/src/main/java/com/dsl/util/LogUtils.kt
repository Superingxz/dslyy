package com.dsl.util

import android.text.TextUtils
import android.util.Log
import com.dsl.extend.util.dslPlusLog

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/26
 * 描述　:
 */
object LogUtils {
    private const val DEFAULT_TAG = "dslPlus_"
    fun debugInfo(tag: String?, msg: String?) {
        if (!dslPlusLog || TextUtils.isEmpty(msg)) {
            return
        }
        if (msg != null) {
            Log.d(tag, msg)
        }
    }

    fun debugInfo(msg: String?) {
        debugInfo(
            DEFAULT_TAG,
            msg
        )
    }

    private fun warnInfo(tag: String?, msg: String?) {
        if (!dslPlusLog || TextUtils.isEmpty(msg)) {
            return
        }
        if (msg != null) {
            Log.w(tag, msg)
        }
    }

    fun warnInfo(msg: String?) {
        warnInfo(
            DEFAULT_TAG,
            msg
        )
    }

    /**
     * 这里使用自己分节的方式来输出足够长度的 message
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    private fun debugLongInfo(tag: String?, msg: String) {
        var msg = msg
        if (!dslPlusLog || TextUtils.isEmpty(msg)) {
            return
        }
        msg = msg.trim { it <= ' ' }
        var index = 0
        val maxLength = 3500
        var sub: String
        while (index < msg.length) {
            sub = if (msg.length <= index + maxLength) {
                msg.substring(index)
            } else {
                msg.substring(index, index + maxLength)
            }
            index += maxLength
            Log.d(tag, sub.trim { it <= ' ' })
        }
    }

    fun debugLongInfo(msg: String) {
        debugLongInfo(
            DEFAULT_TAG,
            msg
        )
    }
}
