package com.dsl.doctor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.dsl.base.viewmodel.BaseViewModel
import com.dsl.doctor.bean.FetchTacticsRequestBean
import com.dsl.doctor.bean.TacticsResponseData
import com.dsl.doctor.network.DoctorplusApi
import com.dsl.doctor.repository.HomepageRepository
import com.dsl.extend.request
import com.dsl.network.getService
import com.dsl.network.vo.AbsentLiveData
import com.dsl.network.vo.Resource
import com.dsl.state.ResultState
import com.dsl.util.BeanUtil

/**
 * @author dsl-abben
 * on 2020/03/02.
 */
class HomepageViewModel : BaseViewModel() {
    private val homepageRepository = HomepageRepository()

    /**
     * 互联网攻略文章
     */
    var nextPageNum = 0L
    val fetchTacticsPageNum = MutableLiveData<Long>()
    val fetchTacticsResponse: LiveData<Resource<TacticsResponseData>>

    var fetchTactics = MutableLiveData<ResultState<TacticsResponseData>>()

    init {
        fetchTacticsResponse = Transformations.switchMap(fetchTacticsPageNum) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                homepageRepository.fetchTactics(FetchTacticsRequestBean(it.toString()))
            }
        }
    }

    /**
     * 第一页
     */
    fun refreshTactics() {
        fetchTacticsData(1L)
    }

    /**
     * 下一页
     */
    fun nextTactics() {
        fetchTacticsData(nextPageNum + 1)
    }

    private fun fetchTacticsData(next: Long) {
        request({
            getService(DoctorplusApi::class.java).fetchTactisList(
                BeanUtil.beanToMap(
                    FetchTacticsRequestBean(next.toString())
                )
            )
        }, fetchTactics)
    }
}