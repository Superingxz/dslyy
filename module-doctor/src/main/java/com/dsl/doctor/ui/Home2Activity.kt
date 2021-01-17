package com.dsl.doctor.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
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
 *     desc  : 主页
 * </pre>
 */
@Route(path = RouterActivityPath.PAGER_DOCTOR_MAIN2)
class Home2Activity : BaseActivity<HomepageViewModel, DoctorActivityMainBinding>() {
    private lateinit var tacticsAdapter: TacticsAdapter
    override fun layoutId(): Int {
        return R.layout.doctor_activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        //设置攻略文章
        tactics_recyclerview.isNestedScrollingEnabled = false
        tactics_recyclerview.layoutManager = LinearLayoutManager(this)
        tactics_recyclerview.addItemDecoration(DividerLine())
        tacticsAdapter = TacticsAdapter( null)
        tacticsAdapter.setOnItemClickListener { adapter, _, position ->
            val itemData = adapter.getItem(position) as TacticsBean
            ToastKit.show(this, itemData.title)
        }
        tactics_recyclerview.adapter = tacticsAdapter
        mViewModel.fetchTactics.observe(this, Observer { resultState ->
            parseState(resultState, {
                if (it.pageNum == 1L) {
                    tacticsAdapter.setNewInstance(it.list.toMutableList())
                } else {
                    tacticsAdapter.addData(it.list.toMutableList())
                }
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })
        mViewModel.fetchTacticsData(1)
    }

    override fun showLoading(message: String) {

    }
}
