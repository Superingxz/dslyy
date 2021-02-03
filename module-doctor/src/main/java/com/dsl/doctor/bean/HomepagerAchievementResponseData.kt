package com.dsl.doctor.bean

/**
 * @author dsl-abben
 * on 2020/03/27.
 */
data class HomepagerAchievementResponseData(
    /**
     * 总接诊量
     */
    val acceptCount: Long,
    /**
     * 今日接诊量
     */
    val todayAcceptCount: Long,
    /**
     * 今日点击量
     */
    val todayVisitCount: Long,
    /**
     * 总评分
     */
    val totalScope: Float,
    /**
     * 总点击量
     */
    val visitCount: Long
)
