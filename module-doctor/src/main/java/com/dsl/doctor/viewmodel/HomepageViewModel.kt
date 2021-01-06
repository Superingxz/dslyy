package com.dsl.doctor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.dsl.base.BaseAndroidViewModel
import com.dsl.doctor.bean.FetchTacticsRequestBean
import com.dsl.doctor.bean.TacticsResponseData
import com.dsl.doctor.repository.HomepageRepository
import com.dsl.network.vo.AbsentLiveData
import com.dsl.network.vo.Resource

/**
 * @author dsl-abben
 * on 2020/03/02.
 */
class HomepageViewModel : BaseAndroidViewModel() {
    private val homepageRepository = HomepageRepository()

    /**
     * 互联网攻略文章
     */
    var nextPageNum = 0L
    val fetchTacticsPageNum = MutableLiveData<Long>()
    val fetchTacticsResponse: LiveData<Resource<TacticsResponseData>>

    init {
        fetchTacticsResponse = Transformations.switchMap(fetchTacticsPageNum) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                homepageRepository.fetchTactics(FetchTacticsRequestBean(it.toString()))
            }
        }
    }
}