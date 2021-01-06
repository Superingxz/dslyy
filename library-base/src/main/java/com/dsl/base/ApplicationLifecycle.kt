package com.dsl.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.dsl.util.DebugLog

/**
 * @author dsl-abben
 * on 2020/03/08.
 */
class ApplicationLifecycle : Application.ActivityLifecycleCallbacks {

    private var started: Int = 0

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
        started++
        if (started == 1) {
            DebugLog.e("应用在前台了！！！")
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        started--
        if (started == 0) {
            DebugLog.e("应用在后台了！！！")
            if (!BaseApplication.isInVideoActivity) {

            }
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}