package com.dsl.base.util

import android.content.Context
import android.content.SharedPreferences
import com.dsl.base.BaseApp
import com.dsl.util.DateTimeUtil

/**
 * 非缓存类SharedPreferences，清除缓存功能并不会清除该xml文件
 *
 * @author dsl-abben
 * on 2020/02/19.
 */
object NonCachedSharedPreferencesManager {
    private const val sharedprefeName = "DoctorPlus.xml"

    private const val keyToken = "keyToken"
    private const val keyLoginUnitTime = "keyLoginUnitTime"

    /**
     * 曾使用过的版本号，为了展示介绍页
     */
    private const val keyLastUseVersion = "keyLastUseVersion"

    /**
     * 医生id
     */
    private const val keyDoctorId = "keyDoctorId"

    /**
     * 是否有中医资质
     */
    private const val keyContainChineseMedicine = "keyContainChineseMedicine"

    /**
     * 是否能开方
     */
    private const val keyEnableRx = "keyEnableRx"

    /**
     * 医师没有开方权限时候的提示文案
     */
    private const val keyUnableRxWarnText = "keyUnableRxWarnText"

    /**
     * 记住密码的账号
     */
    private const val keyAccount = "keyAccount"

    /**
     * 记住密码时候的账号
     */
    private const val keyPassword = "keyPassword"

    /**
     * 是否显示关闭接诊时候的弹框
     */
    private const val keyOnlineTip = "keyOnlineTip"

    @Volatile
    private var sharedPreferences: SharedPreferences? = null

    private fun getNonCachedSharedPreferences(): SharedPreferences {
        return sharedPreferences ?: synchronized(this) {
            sharedPreferences ?: BaseApp.getInstance().getSharedPreferences(
                sharedprefeName,
                Context.MODE_PRIVATE
            )
        }
    }

    fun setToken(token: String) {
        getNonCachedSharedPreferences().edit().putString(keyToken, token).apply()
        if (token.isNotEmpty()) {
            // 记录该次登录时间，防止刚上线就被同步接诊状态，需要刚上线调整为接诊
            getNonCachedSharedPreferences().edit()
                .putLong(keyLoginUnitTime, DateTimeUtil.getUnixTime()).apply()
        }
    }

    fun getToken(): String {
        return getNonCachedSharedPreferences().getString(keyToken, "") ?: ""
    }

    fun getLastLoginUnitTime(): Long {
        return getNonCachedSharedPreferences().getLong(keyLoginUnitTime, DateTimeUtil.getUnixTime())
    }

    fun setLastUseVersion(version: String) {
        getNonCachedSharedPreferences().edit().putString(keyLastUseVersion, version).apply()
    }

    fun getLastUseVersion(): String {
        return getNonCachedSharedPreferences().getString(keyLastUseVersion, "") ?: ""
    }

    fun setAccountAndPassword(account: String, password: String) {
        getNonCachedSharedPreferences().edit().putString(keyAccount, account).apply()
        getNonCachedSharedPreferences().edit().putString(keyPassword, password).apply()
    }

    fun getAccount(): String {
        return getNonCachedSharedPreferences().getString(keyAccount, "") ?: ""
    }

    fun getPassword(): String {
        return getNonCachedSharedPreferences().getString(keyPassword, "") ?: ""
    }

    fun setContainChineseMedicine(professionalType: String) {
        // 0西医 1中医 2中西医
        getNonCachedSharedPreferences().edit()
            .putBoolean(keyContainChineseMedicine, professionalType != "0").apply()
    }

    fun isContainChineseMedicine(): Boolean {
        return getNonCachedSharedPreferences().getBoolean(keyContainChineseMedicine, false)
    }

    fun setDoctorId(doctorId: Long) {
        getNonCachedSharedPreferences().edit().putLong(keyDoctorId, doctorId).apply()
    }

    fun getDoctorId(): Long {
        return getNonCachedSharedPreferences().getLong(keyDoctorId, 0L)
    }

    fun setShowOnlineTip(show: Boolean) {
        getNonCachedSharedPreferences().edit().putBoolean(keyOnlineTip, show).apply()
    }

    fun isShowOnlineTip(): Boolean {
        return getNonCachedSharedPreferences().getBoolean(keyOnlineTip, true)
    }

    fun setEnableRx(enableRx: Boolean) {
        getNonCachedSharedPreferences().edit().putBoolean(keyEnableRx, enableRx).apply()
    }

    fun isEnableRx(): Boolean {
        return getNonCachedSharedPreferences().getBoolean(keyEnableRx, true)
    }

    fun setUnableRxWarnText(unableRxWarnText: String) {
        getNonCachedSharedPreferences().edit().putString(keyUnableRxWarnText, unableRxWarnText)
            .apply()
    }

    fun getUnableRxWarnText(): String {
        return getNonCachedSharedPreferences().getString(keyUnableRxWarnText, "") ?: ""
    }
}
