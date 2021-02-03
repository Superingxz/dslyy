package com.dsl.doctor.bean

/**
 * @author dsl-abben
 * on 2020/03/03.
 */
data class TacticsResponseData(
    val next: Boolean,
    val pageNum: Long,
    val list: List<TacticsBean>
)

data class TacticsBean(
    /**
     * 创建时间
     */
    val createTime: Long,
    val id: Long,
    /**
     * 阅读量
     */
    val readNum: Long,
    /**
     * 标题
     */
    val title: String,
    /**
     * 封面
     */
    val top: String
)
