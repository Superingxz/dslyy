package com.dsl.doctorplus.constant

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
     * 点击悬浮窗后发送的显示详情取消与接诊的dialog的广播意图
     */
    const val ACTION_SHOW_VIDEO_DIALOG = "android.intent.action.dsldoctor.ACTION_SHOW_VIDEO_DIALOG"

    /**
     * 通知视频咨询Activity，Im被挤退的广播意图
     */
    const val ACTION_IM_FORCE_OFFLINE = "android.intent.action.dsldoctor.ACTION_IM_FORCE_OFFLINE"

    /**
     * 通知刷新问诊数量的广播意图
     */
    const val ACTION_REFRESH_INQUIRY_COUNT = "android.intent.action.dsldoctor.ACTION_REFRESH_INQUIRY_COUNT"

    /**
     * 通知刷新接诊状态的广播意图
     */
    const val ACTION_REFRESH_ONLINE = "android.intent.action.dsldoctor.ACTION_REFRESH_ONLINE"

    /**
     * 通知首页取消待接诊的悬浮窗广播意图
     */
    const val ACTION_CANCEL_WINDOW = "android.intent.action.dsldoctor.ACTION_CANCEL_WINDOW"

    /**
     * 腾讯云IM服务器角色名字，会发送刷新问诊数量、有新视频问诊等通知
     */
    const val IM_SERVICE_NAME = "administrator"

    /**
     * 腾讯云IM自定义通知刷新数量
     */
    const val IM_TYPE_CUSTOM_COUNT = "doctorCount"

    /**
     * 腾讯云IM自定义通知有视频要接诊
     */
    const val IM_TYPE_CUSTOM_CALL = "doctorCall"

    /**
     * 处方Id
     */
    const val PRESCRIPTION_ID = "PRESCRIPTION_ID"

    /**
     * 快速图文问诊，最长候诊1小时
     */
    const val maxChatWaitSeconds = 3600

    /**
     *快速图文问诊，最长问诊20分钟
     */
    const val maxChatIngSeconds = 1200

    /**
     * 预约图文问诊，最长候诊48小时
     */
    const val maxChatBookWaitSeconds = 172800
    /**
     *预约图文问诊，最长问诊48小时
     */
    const val maxChatBookIngSeconds = 172800

    /**
     * 快速视频，最长候诊1小时
     */
    const val maxVideoWaitSeconds = 3600

    /**
     *快速视频，最长问诊20分钟
     */
    const val maxVideoIngSeconds = 1200

    /**
     * 预约视频问诊，最长问诊60分钟
     */
    const val maxVideoBookIngSeconds = 3600

    /**
     * 极光推送关于别名与标签操作的SEQUENCE
     */
    const val  DELETE_ALIAS_SEQUENCE = 10086
    const val  SET_ALIAS_SEQUENCE = 10087
    const val  DELETE_TAG_SEQUENCE = 10088
    const val  SET_TAG_SEQUENCE = 10089
}