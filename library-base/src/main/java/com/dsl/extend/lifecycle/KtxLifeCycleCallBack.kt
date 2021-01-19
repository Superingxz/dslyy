package com.dsl.extend.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.dsl.base.BaseApp
import com.dsl.util.DebugLog
import com.dsl.extend.util.logd

/**
 * 作者　: hegaojian
 * 时间　: 20120/1/7
 * 描述　:
 */
class KtxLifeCycleCallBack : Application.ActivityLifecycleCallbacks {
    private var started: Int = 0
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        KtxActivityManger.pushActivity(activity)
        "onActivityCreated : ${activity.localClassName}".logd()
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.localClassName}".logd()
        started++
        if (started == 1) {
            DebugLog.e("应用在前台了！！！")
        }
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.localClassName}".logd()
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.localClassName}".logd()
    }


    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.localClassName}".logd()
        KtxActivityManger.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.localClassName}".logd()
        started--
        if (started == 0) {
            DebugLog.e("应用在后台了！！！")
            if (!BaseApp.isInVideoActivity) {

            }
        }
    }
}