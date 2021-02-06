package com.dsl.base.util

import android.text.TextUtils
import com.google.gson.Gson

/**
 * @author dsl-abben
 * on 2020/02/19.
 */
open class BaseRequestBean {
    val app_token: String = NonCachedSharedPreferencesManager.getToken()

    /**
     * 平台标识，Andorid、iOS、H5、Server、WXApp、Web 等
     */
    val platform: String = "Android"

    /**
     * 移动设备 ID（预留字段）
     */
    val deviceId: String = ""

    /**
     * 请求当前时间戳（单位：秒）
     */
    val timeStamp: String = System.currentTimeMillis().toString()

    /**
     * 接口版本号（默认 1.0）
     */
    val version: String = "1.0"

    /**
     * 应用唯一标识
     */
    val appId: String = ""

    /**
     * 数字签名
     */
    var sign: String = ""
        set(value) {
            field = value
        }
        get() {
            val jsonMap = if (!TextUtils.isEmpty(field)) {
                Gson().fromJson(field, MutableMap::class.java)
            } else {
                mutableMapOf<String, String>()
            }
            return getSign(
                timeStamp,
                appId,
                accessToken,
                platform,
                version,
                jsonMap as MutableMap<String, String>
            )
        }

    /**
     * 访问令牌，标识已登录用户（或医生）的唯一标识。 需要先登录的接口都需要传输该参数
     */
    val accessToken: String =
        NonCachedSharedPreferencesManager.getToken()

    /**
     * 刷新令牌（预留字段）
     */
    val refreshToken: String = ""

    /**
     * JSON 字符格式，分页参数
     */
    val page: Page =
        Page()

    private fun getSign(
        timeStamp: String,
        appId: String,
        accessToken: String,
        platform: String,
        version: String,
        requestMap: MutableMap<String, String>
    ): String {
        var sign: String = ""
        return sign
    }
}

/**
 * pageIndex 当前页索引，不填默认为 1
 * pageSize  每页条数，不填默认为 20
 */
data class Page(val pageIndex: Int = 1, val pageSize: Int = 20)
