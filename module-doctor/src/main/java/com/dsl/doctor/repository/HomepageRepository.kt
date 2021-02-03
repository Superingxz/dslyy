package com.dsl.doctor.repository

import androidx.lifecycle.LiveData
import com.dsl.base.BaseRepository
import com.dsl.base.BaseRequestBean
import com.dsl.doctor.bean.FetchTacticsRequestBean
import com.dsl.doctor.bean.HomepageModuleResponseData
import com.dsl.doctor.bean.HomepagerAchievementResponseData
import com.dsl.doctor.bean.TacticsResponseData
import com.dsl.doctor.network.DoctorplusApi
import com.dsl.network.ApiManager
import com.dsl.network.NetworkBoundResource
import com.dsl.network.vo.ApiResponse
import com.dsl.network.vo.RealResponseBody
import com.dsl.network.vo.Resource
import com.dsl.util.BeanUtil

/**
 * @author dsl-abben
 * on 2020/03/02.
 */
class HomepageRepository : BaseRepository() {

    fun fetchHomepageModule(): LiveData<Resource<HomepageModuleResponseData>> {
        return object :
            NetworkBoundResource<HomepageModuleResponseData, RealResponseBody<HomepageModuleResponseData>>() {
            override fun createCall(): LiveData<ApiResponse<RealResponseBody<HomepageModuleResponseData>>> {
                return ApiManager.getService(DoctorplusApi::class.java)
                    .fetchHomepageModule(BeanUtil.beanToMap(BaseRequestBean()))
            }

            override fun onFetchFailed() {
            }

            override fun onFetchSuccess(item: HomepageModuleResponseData?) {
            }
        }.asLiveData()
    }

    fun fetchTactics(requestBean: FetchTacticsRequestBean): LiveData<Resource<TacticsResponseData>> {
        return object :
            NetworkBoundResource<TacticsResponseData, RealResponseBody<TacticsResponseData>>() {
            override fun createCall(): LiveData<ApiResponse<RealResponseBody<TacticsResponseData>>> {
                return ApiManager.getService(DoctorplusApi::class.java)
                    .fetchTactics(BeanUtil.beanToMap(requestBean))
            }

            override fun onFetchFailed() {
            }

            override fun onFetchSuccess(item: TacticsResponseData?) {
            }
        }.asLiveData()
    }

    fun fetchHomepagerAchievement(): LiveData<Resource<HomepagerAchievementResponseData>> {
        return object :
            NetworkBoundResource<HomepagerAchievementResponseData, RealResponseBody<HomepagerAchievementResponseData>>() {
            override fun createCall(): LiveData<ApiResponse<RealResponseBody<HomepagerAchievementResponseData>>> {
                return ApiManager.getService(DoctorplusApi::class.java)
                    .fetchHomepagerAchievement(BeanUtil.beanToMap(BaseRequestBean()))
            }

            override fun onFetchFailed() {
            }

            override fun onFetchSuccess(item: HomepagerAchievementResponseData?) {
            }
        }.asLiveData()
    }
}
