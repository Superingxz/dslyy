package com.dsl.constant

/**
 * 全局变量
 */
object GlobalConstants {

    /**
     * 微信appID
     */
    const val WXAPI_APPID = "wx4845e3e9d701f1a8"

    /**
     * 下载安装包失败的广播意图，如被挟持了
     */
    const val ACTION_DOWNLOADAPK_FAIL = "android.intent.action.dsldoctor.ACTION_DOWNLOADAPK_FAIL"

    /**
     * 极光推送关于别名与标签操作的SEQUENCE
     */
    const val DELETE_ALIAS_SEQUENCE = 10086
    const val SET_ALIAS_SEQUENCE = 10087
    const val DELETE_TAG_SEQUENCE = 10088
    const val SET_TAG_SEQUENCE = 10089
}
