package com.dsl.network.universal

/**
 * Im服务器定义的自定义通知
 * @author dsl-abben
 * on 2020/03/06.
 */
data class ImCustomBean(
    /**
     * 类型
     * doctorCount 新订单通知
     * doctorCall 医生呼叫通知
     * acceptChange 接诊状态改变
     */
    val type: String,
    /**
     * 0 订单变动 1 新问诊
     */
    val sound:Int,
    /**
     * 图文问诊接诊中人数
     */
    val imgPatientNum: Long,
    /**
     * 图文问诊待接诊人数
     */
    val imgPatientWaitNum: Long,
    /**
     * 视频问诊接诊中人数
     */
    val videoPatientNum:Long,
    /**
     * 视频问诊待接诊人数
     */
    val videoPatientWaitNum: Long,
    /**
     * 订单状态，0 候诊中 1 问诊中 2 超时未接诊 3 医生取消 4 用户取消 5 问诊结束
     */
    val orderStatus:Int,
    /**
     * 问诊类型	1 图文 2 视频
     */
    val orderType:Int,
    /**
     * 视频拨号时候的订单号
     */
    val orderId: String,
    /**
     * 视频拨号时候的房间号
     */
    val roomId: String,
    /**
     * 接诊状态改变后是否接诊 0否 1是
     */
    val acceptStatus:String
)