package com.dsl.base

import com.dsl.doctorplus.util.NonCachedSharedPreferencesManager

/**
 * @author dsl-abben
 * on 2020/02/19.
 */
open class BaseRequestBean{
    val app_token:String = NonCachedSharedPreferencesManager.getToken()
}