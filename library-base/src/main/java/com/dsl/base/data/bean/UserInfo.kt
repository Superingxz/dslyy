package com.dsl.base.data.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/1/21 14:54
 *     desc  : 账户信息
 * </pre>
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class UserInfo(
    var id: String = "",
    var token: String = "",
    var nickname: String = "",
    var username: String = ""
) : Parcelable
