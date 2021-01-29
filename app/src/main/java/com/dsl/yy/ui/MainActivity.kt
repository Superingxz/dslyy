package com.dsl.yy.ui

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.dsl.base.BaseActivity
import com.dsl.constant.RouterActivityPath
import com.dsl.yy.R
import com.dsl.yy.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterActivityPath.PAGER_MAIN)
class MainActivity : BaseActivity<HomeViewModel>() {
    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun subscribeUi(viewModel: HomeViewModel) {
    }

    override fun initView() {
        jump.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.jump -> {
                ARouter.getInstance().build(RouterActivityPath.PAGER_DOCTOR_MAIN2)
                    .navigation()
            }
        }
    }

    override fun showLoading(message: String) {
    }
}