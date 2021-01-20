package com.dsl.doctor.network

import androidx.lifecycle.LiveData
import com.dsl.base.BaseApiResponse
import com.dsl.doctor.bean.HomepageModuleResponseData
import com.dsl.doctor.bean.HomepagerAchievementResponseData
import com.dsl.doctor.bean.TacticsResponseData
import com.dsl.network.vo.ApiResponse
import com.dsl.network.vo.RealResponseBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * @author dsl-abben
 * on 2020/03/01.
 */
interface DoctorplusApi {
    /**
     * 获取首页模块
     */
    @GET("app/appResource/getIndexItem.do")
    fun fetchHomepageModule(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): LiveData<ApiResponse<RealResponseBody<HomepageModuleResponseData>>>

    /**
     * 获取互联网攻略列表
     */
    @GET("app/tactic/getTacticPage.do")
    fun fetchTactics(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): LiveData<ApiResponse<RealResponseBody<TacticsResponseData>>>

    /**
     * 获取互联网攻略列表
     */
    @GET("app/tactic/getTacticPage.do")
    suspend fun fetchTactisList(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): BaseApiResponse<TacticsResponseData>

    /**
     * 首页执业成就
     */
    @GET("app/doctor/getIndexAchievement.do")
    fun fetchHomepagerAchievement(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): LiveData<ApiResponse<RealResponseBody<HomepagerAchievementResponseData>>>
}