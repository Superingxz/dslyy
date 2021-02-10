package com.dsl.base.util

import android.text.TextUtils
import com.dsl.base.data.bean.UserInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/1/21 14:47
 *     desc  : 缓存工具类
 * </pre>
 */
object CacheUtil {
    private const val appId = "app"
    private const val cacheId = "cache"
    private const val APP_TOKEN = "keyToken"
    private const val APP_USER = "user"
    private const val APP_LOGIN = "login"
    private const val APP_FIRST = "first"
    private const val CACHE_HISTORY = "history"

    /**
     * 获取保存的账户信息
     */
    fun getUser(): UserInfo? {
        val kv = MMKV.mmkvWithID(appId)
        val userStr = kv.decodeString(APP_USER)
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, UserInfo::class.java)
        }
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: UserInfo?) {
        val kv = MMKV.mmkvWithID(appId)
        if (userResponse == null) {
            kv.encode(APP_USER, "")
            setIsLogin(false)
        } else {
            kv.encode(APP_USER, Gson().toJson(userResponse))
            setIsLogin(true)
        }
    }

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID(appId)
        return kv.decodeBool(APP_LOGIN, false)
    }

    /**
     * 设置是否已经登录
     */
    fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID(appId)
        kv.encode(APP_LOGIN, isLogin)
    }

    /**
     * 是否是第一次登陆
     */
    fun isFirst(): Boolean {
        val kv = MMKV.mmkvWithID(appId)
        return kv.decodeBool(APP_FIRST, true)
    }

    /**
     * 是否是第一次登陆
     */
    fun setFirst(first: Boolean): Boolean {
        val kv = MMKV.mmkvWithID(appId)
        return kv.encode(APP_FIRST, first)
    }

    /**
     * 设置token
     */
    fun setToken(token: String): Boolean {
        val kv = MMKV.mmkvWithID(appId)
        return kv.encode(APP_TOKEN, token)
    }

    /**
     * 获取token
     */
    fun getToken(): String {
        val kv = MMKV.mmkvWithID(appId)
        return kv.decodeString(APP_TOKEN)
    }

    /**
     * 获取搜索历史缓存数据
     */
    fun getSearchHistoryData(): ArrayList<String> {
        val kv = MMKV.mmkvWithID(cacheId)
        val searchCacheStr = kv.decodeString(CACHE_HISTORY)
        if (!TextUtils.isEmpty(searchCacheStr)) {
            return Gson().fromJson(
                searchCacheStr, object : TypeToken<ArrayList<String>>() {}.type
            )
        }
        return arrayListOf()
    }

    fun setSearchHistoryData(searchResponseStr: String) {
        val kv = MMKV.mmkvWithID(cacheId)
        kv.encode(CACHE_HISTORY, searchResponseStr)
    }
}
