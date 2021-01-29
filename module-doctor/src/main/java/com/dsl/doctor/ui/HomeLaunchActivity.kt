package com.dsl.doctor.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.dsl.base.activity.BaseActivity
import com.dsl.constant.RouterActivityPath
import com.dsl.doctor.R
import com.dsl.doctor.adapter.TacticsAdapter
import com.dsl.doctor.bean.TacticsBean
import com.dsl.doctor.databinding.DoctorActivityMainBinding
import com.dsl.doctor.viewmodel.HomepageViewModel
import com.dsl.extend.parseState
import com.dsl.util.ToastKit
import com.dsl.widget.DividerLine
import kotlinx.android.synthetic.main.doctor_activity_main.*

/**
 * <pre>
 *     author: MoYaoZhi
 *     email : 1803117759@qq.com
 *     time  : 2021/1/17
 *     desc  : 主页(协程)
 * </pre>
 */
@Route(path = RouterActivityPath.PAGER_DOCTOR_MAIN2)
class HomeLaunchActivity : BaseActivity<HomepageViewModel, DoctorActivityMainBinding>(),
    OnLoadMoreListener {
    private lateinit var tacticsAdapter: TacticsAdapter
    override fun layoutId(): Int {
        return R.layout.doctor_activity_main
    }

    override fun createObserver() {
        mViewModel.fetchTactics.observe(this, Observer { resultState ->
            parseState(resultState, {
                tacticsAdapter.loadMoreModule.loadMoreEnd(!it.next || it.list.isEmpty())
                mViewModel.nextPageNum = it.pageNum
                if (it.pageNum == 1L) {
                    tacticsAdapter.setNewInstance(it.list.toMutableList())
                } else {
                    tacticsAdapter.addData(it.list.toMutableList())
                }
            }, {
                showToast(it.errorMsg)
            })
        })
        mViewModel.refreshTactics()
    }

    override fun initView(savedInstanceState: Bundle?) {
        //强烈注意：使用addLoadingObserve 将非绑定当前activity的viewmodel绑定loading回调 防止出现请求时不显示 loading 弹窗bug
        addLoadingObserve(mViewModel)
        //设置攻略文章
        tactics_recyclerview.isNestedScrollingEnabled = false
        tactics_recyclerview.layoutManager = LinearLayoutManager(this)
        tactics_recyclerview.addItemDecoration(DividerLine())
        tacticsAdapter = TacticsAdapter(null)
        tacticsAdapter.setOnItemClickListener { adapter, _, position ->
            val itemData = adapter.getItem(position) as TacticsBean
            ToastKit.show(this, itemData.title)
        }
        tacticsAdapter.loadMoreModule.setOnLoadMoreListener(this)
        tactics_recyclerview.adapter = tacticsAdapter
    }

    override fun showLoading(message: String) {
    }

    override fun onLoadMore() {
        mViewModel.nextTactics()
    }
}
