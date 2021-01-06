package com.dsl.util

import android.util.Log
import com.dsl.network.ApiManager

/**
 * @author dsl-abben
 * on 2020/02/14.
 */
object DebugLog {
    private const val tag = "DoctorPlus_"

    fun d(message: String) {
        if (ApiManager.showDebug) {
            Log.d(tag, message)
        }
    }

    fun i(message: String) {
        if (ApiManager.showDebug) {
            Log.i(tag, message)
        }
    }

    fun w(message: String) {
        if (ApiManager.showDebug) {
            Log.w(tag, message)
        }
    }

    fun e(message: String) {
        if (ApiManager.showDebug) {
            Log.e(tag, message)
        }
    }
}