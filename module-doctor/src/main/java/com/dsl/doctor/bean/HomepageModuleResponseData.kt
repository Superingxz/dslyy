package com.dsl.doctor.bean

/**
 * @author dsl-abben
 * on 2020/03/02.
 */
data class HomepageModuleResponseData(
    /**
     * 问诊类型
     */
    val consultationItem: List<ModuleBean>
)

data class ModuleBean(
    val code: String,
    val enable: Int,
    val id: Long,
    val imgPath: String,
    val name: String,
    val showOrder: Int,
    val type: Int,
    val uri: String,
    /**
     * 问诊数量
     */
    var count: Long
)
