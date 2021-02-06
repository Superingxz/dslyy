package com.dsl.doctor.bean

import com.dsl.base.util.BaseRequestBean

/**
 * @author dsl-abben
 * on 2020/03/03.
 */
data class FetchTacticsRequestBean(
    var pageNum: String,
    val pageSize: String = "20"
) : BaseRequestBean()
