package com.dsl.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.dsl.util.StatusBarUtil
import com.dsl.widget.LoadingDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author dsl-abben
 * on 2020/02/14.
 */
abstract class BaseActivity<E : BaseAndroidViewModel> : AppCompatActivity(), View.OnClickListener {
    protected abstract fun getContentViewId(): Int

    /**
     * 绑定ViewModel与View,
     * 当数据改变时通知View
     *
     * @param viewModel viewModel
     */
    protected abstract fun subscribeUi(viewModel: E)

    protected abstract fun initView()

    @Suppress("UNCHECKED_CAST")
    protected val mViewModel: E by lazy {
        //这里获得到的是类的泛型的类型
        val type: Type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        ViewModelProvider(this)[type as Class<E>]
    }

    var compositeDisposable: CompositeDisposable? = null
    private var loadingDialog: LoadingDialog? = null

    /**
     * 当前activity的生命周期，用于广播接收器监听时候是否弹出视频接听dailog
     */
    var nowLifeEvent: Lifecycle.Event = Lifecycle.Event.ON_CREATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setContentView(getContentViewId())
        compositeDisposable = CompositeDisposable()
        subscribeBaseUi()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    private fun setStatusBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white)
        StatusBarUtil.setLightStatusBar(this, true, false)
    }

    private fun subscribeBaseUi() {
        subscribeUi(mViewModel)
    }

    override fun onRestart() {
        super.onRestart()
        nowLifeEvent = Lifecycle.Event.ON_CREATE
    }

    override fun onPause() {
        super.onPause()
        nowLifeEvent = Lifecycle.Event.ON_PAUSE
    }

    fun showToast(message: String?) {
        dismissLoading()
        message?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }

    fun showToast(@StringRes resId: Int) {
        dismissLoading()
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    fun showPostLoading() {
        loadingDialog = LoadingDialog.onNewInstance(getString(R.string.post_loading))
        loadingDialog?.show(supportFragmentManager, "PostLoading")
    }

    fun showFetchLoading() {
        loadingDialog = LoadingDialog.onNewInstance(getString(R.string.fetch_loading))
        loadingDialog?.show(supportFragmentManager, "PostLoading")
    }

    private fun dismissLoading() {
        loadingDialog?.dismiss()
    }
}