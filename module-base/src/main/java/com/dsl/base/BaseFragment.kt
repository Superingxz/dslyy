package com.dsl.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsl.network.manager.NetState
import com.dsl.network.manager.NetworkStateManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author dsl-abben
 * on 2020/02/20.
 */
abstract class BaseFragment<E : BaseAndroidViewModel> : Fragment(), View.OnClickListener {
    // 是否第一次加载
    private var isFirst: Boolean = true

    protected abstract fun getContentViewId(): Int

    /**
     * 是否与其他同一层次的fragment共用ViewModel
     */
    protected abstract fun isShareViewModel(): Boolean

    /**
     * 绑定ViewModel与View,
     * 当数据改变时通知View
     *
     * @param viewModel viewModel
     */
    protected abstract fun subscribeUi(viewModel: E)

    protected abstract fun initView()

    private var mViewModel: E? = null
    var compositeDisposable: CompositeDisposable? = null
    private var loadingDialog: com.dsl.base.widget.LoadingDialog? = null

    private fun getViewModel(): E {
        return if (mViewModel != null) mViewModel!! else {
            // 这里获得到的是类的泛型的类型
            val type: Type =
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
            if (isShareViewModel()) {
                ViewModelProvider(requireActivity())[type as Class<E>]
            } else {
                ViewModelProvider(this)[type as Class<E>]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(getContentViewId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        subscribeBaseUi()
        initView()
        createObserver()
        onVisible()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载0.12秒加载 避免fragment跳转动画和渲染ui同时进行，出现些微的小卡顿
            view?.postDelayed(
                {
                    lazyLoadData()
                    // 在Fragment中，只有懒加载过了才能开启网络变化监听
                    NetworkStateManager.instance.mNetworkStateCallback.observe(
                        viewLifecycleOwner,
                        Observer {
                            // 不是首次订阅时调用方法，防止数据第一次监听错误
                            if (!isFirst) {
                                onNetworkStateChanged(it)
                            }
                        }
                    )
                    isFirst = false
                },
                120
            )
        }
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    private fun subscribeBaseUi() {
        subscribeUi(getViewModel())
    }

    fun showToast(message: String?) {
        loadingDialog?.dismiss()
        message?.let { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() }
    }

    fun showToast(@StringRes resId: Int) {
        loadingDialog?.dismiss()
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
    }

    fun showPostLoading() {
        activity?.let {
            loadingDialog = com.dsl.base.widget.LoadingDialog.onNewInstance(getString(
                R.string.post_loading
            ))
            loadingDialog?.show(it.supportFragmentManager, "PostLoading")
        }
    }

    fun showPostLoading(loadingMessage: String) {
        activity?.let {
            loadingDialog = com.dsl.base.widget.LoadingDialog.onNewInstance(loadingMessage)
            loadingDialog?.show(it.supportFragmentManager, "PostLoading")
        }
    }

    fun showfetchLoading() {
        activity?.let {
            loadingDialog = com.dsl.base.widget.LoadingDialog.onNewInstance(getString(
                R.string.fetch_loading
            ))
            loadingDialog?.show(it.supportFragmentManager, "PostLoading")
        }
    }

    fun dismissPostLoading() {
        loadingDialog?.dismiss()
    }
}
