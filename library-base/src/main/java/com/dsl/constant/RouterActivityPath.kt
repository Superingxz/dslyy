package com.dsl.constant

/**
 * <pre>
 *     author: myz
 *     email : 1803117759@qq.com
 *     time  : 2021/1/6 17:51
 *     desc  : 各个组件中的路径路由
 * </pre>
 */
class RouterActivityPath private constructor() {
    companion object Main {
        private const val MAIN = "/main"
        private const val DOCTOR = "/doctor"

        /*主业务界面*/
        const val PAGER_MAIN = "$MAIN/Main"

        const val PAGER_DOCTOR_MAIN = "$DOCTOR/Home"
        const val PAGER_DOCTOR_MAIN2 = "$DOCTOR/Home2"
    }
}
