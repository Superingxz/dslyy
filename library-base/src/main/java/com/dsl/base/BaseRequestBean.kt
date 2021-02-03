package com.dsl.base

/**
 * @author dsl-abben
 * on 2020/02/19.
 */
open class BaseRequestBean {
    val app_token: String = com.dsl.util.NonCachedSharedPreferencesManager.getToken()
}
