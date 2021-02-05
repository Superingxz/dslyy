package com.dsl.base.fragment

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dsl.base.util.DebugLog

/**
 * try.catch弹框显示与消失时候IllegalStateException错误，参考https://www.jianshu.com/p/f6570ce9e413
 * @author dsl-abben
 * on 2020/10/30.
 */
open class BaseDialogFragment : DialogFragment() {

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: IllegalStateException) {
            DebugLog.e("${javaClass.name} show error:$e")
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: IllegalStateException) {
            DebugLog.e("${javaClass.name} dismiss error:$e")
        }
    }
}
