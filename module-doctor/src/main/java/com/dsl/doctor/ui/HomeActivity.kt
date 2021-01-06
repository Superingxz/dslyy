package com.dsl.doctor.ui

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dsl.base.BaseActivity
import com.dsl.constant.RouterActivityPath
import com.dsl.doctor.R
import com.dsl.doctor.adapter.TacticsAdapter
import com.dsl.doctor.bean.TacticsBean
import com.dsl.doctor.viewmodel.HomepageViewModel
import com.dsl.network.vo.Status
import com.dsl.util.ToastKit
import com.dsl.widget.DividerLine
import kotlinx.android.synthetic.main.doctor_activity_main.*

/**
 * <pre>
 *     author: myz
 *     email : 1803117759@qq.com
 *     time  : 2021/1/6 19:04
 *     desc  : 主页
 * </pre>
 */
@Route(path = RouterActivityPath.PAGER_DOCTOR_MAIN)
class HomeActivity : BaseActivity<HomepageViewModel>(),
    BaseQuickAdapter.RequestLoadMoreListener {
    private lateinit var tacticsAdapter: TacticsAdapter
    override fun getContentViewId(): Int {
        return R.layout.doctor_activity_main
    }

    override fun subscribeUi(viewModel: HomepageViewModel) {
        viewModel.fetchTacticsResponse.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        tacticsAdapter.loadMoreEnd(
                            !data.next || data.list.isEmpty()
                        )
                        if (data.pageNum == 1L) {
                            tacticsAdapter.setNewData(data.list)
                        } else {
                            tacticsAdapter.addData(data.list)
                        }
                        mViewModel.nextPageNum = data.pageNum + 1
                        tacticsAdapter.loadMoreComplete()
                    }
                }
                Status.ERROR -> {
                    showToast(it.message)
                }
                Status.LOADING -> {
                }
            }
        })
    }

    override fun initView() {
        //设置攻略文章
        tactics_recyclerview.isNestedScrollingEnabled = false
        tactics_recyclerview.layoutManager = LinearLayoutManager(this)
        tactics_recyclerview.addItemDecoration(DividerLine())
        tacticsAdapter = TacticsAdapter(null)
        tacticsAdapter.setOnLoadMoreListener(this, tactics_recyclerview)
        tacticsAdapter.setOnItemClickListener { adapter, _, position ->
            val itemData = adapter.getItem(position) as TacticsBean
            ToastKit.show(this, itemData.title)
        }
        tactics_recyclerview.adapter = tacticsAdapter
        mViewModel.fetchTacticsPageNum.value = 1
    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchTacticsPageNum
    }

    override fun onClick(v: View?) {
    }

    override fun onLoadMoreRequested() {
        mViewModel.fetchTacticsPageNum.value = mViewModel.nextPageNum
    }
}