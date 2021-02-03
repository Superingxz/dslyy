package com.dsl.base.activity

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import com.dsl.base.event.AppViewModel
import com.dsl.base.event.EventViewModel
import com.dsl.base.viewmodel.BaseViewModel
import com.dsl.extend.dismissLoadingExt
import com.dsl.extend.getAppViewModel
import com.dsl.extend.showLoadingExt
import com.dsl.util.ToastKit
import me.jessyan.autosize.AutoSizeCompat

/**
 * 时间　: 2019/12/21
 * 作者　: hegaojian
 * 描述　: 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    // Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    // Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    fun showToast(message: String?) {
        dismissLoading()
        message?.let { ToastKit.show(this, it) }
    }

    fun showToast(@StringRes resId: Int) {
        dismissLoading()
        ToastKit.show(this, resId)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    /**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     */
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }
}
