package com.dsl.network.universal

import com.dsl.base.BaseRequestBean

/**
 * @author dsl-abben
 * on 2020/02/25.
 */
data class FetchListBaseRequestBean(
    var pageNum: String,
    val pageSize: String = "20"
) : BaseRequestBean()