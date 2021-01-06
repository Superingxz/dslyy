package com.dsl.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dsl.widget.LoadingDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author dsl-abben
 * on 2020/02/20.
 */
abstract class BaseFragment<E : BaseAndroidViewModel> : Fragment(), View.OnClickListener {

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
    private var loadingDialog: LoadingDialog? = null

    fun getViewModel(): E {
        return if (mViewModel != null) mViewModel!! else {
            //这里获得到的是类的泛型的类型
            val type: Type =
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
            if (isShareViewModel()) {
                ViewModelProvider(activity!!)[type as Class<E>]
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
    }

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
            loadingDialog = LoadingDialog.onNewInstance(getString(R.string.post_loading))
            loadingDialog?.show(it.supportFragmentManager, "PostLoading")
        }
    }

    fun showPostLoading(loadingMessage: String) {
        activity?.let {
            loadingDialog = LoadingDialog.onNewInstance(loadingMessage)
            loadingDialog?.show(it.supportFragmentManager, "PostLoading")
        }
    }

    fun showfetchLoading() {
        activity?.let {
            loadingDialog = LoadingDialog.onNewInstance(getString(R.string.fetch_loading))
            loadingDialog?.show(it.supportFragmentManager, "PostLoading")
        }
    }

    fun dismissPostLoading() {
        loadingDialog?.dismiss()
    }
}