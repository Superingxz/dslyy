package com.dsl.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dsl.util.DebugLog
import com.dsl.network.vo.ApiResponse
import com.dsl.network.vo.RealResponseBody
import com.dsl.network.vo.Resource
import com.dsl.util.NonCachedSharedPreferencesManager

/**
 * 参考谷歌应用架构指南 https://developer.android.google.cn/jetpack/docs/guide
 *  @author abben
 *  on 2020/02/14
 */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        val remoteSource = this.createCall()
        result.addSource(remoteSource) {
            result.removeSource(remoteSource)
            //检查数据加载状态
            if (it?.code in 200..299) {
                //http状态码正常，然后判断我们服务器自定义的状态码
                when (if (it?.body is RealResponseBody<*>) (it.body as RealResponseBody<*>).status else -600) {
                    200 -> {
                        //成功
                        val resultType = processResponse(it)
                        setValue(
                            Resource.success(
                                if (it?.body is RealResponseBody<*>) {
                                    it.body.message
                                } else null,
                                resultType
                            )
                        )
                        DebugLog.i("NetworkBoundResource响应体: $resultType")
                        onFetchSuccess(resultType)
                    }
                    300 -> {
                        //token失效
                        NonCachedSharedPreferencesManager.setToken("")
                        //跳转登录页面
                    }
                    else -> {
                        DebugLog.e("NetworkBoundResource->服务器自定义错误message:" + it?.errorMessage + "\n响应体:" + it?.body)
                        if (it?.body is RealResponseBody<*>) {
                            if (it.body.message == "?") {//自定义错误类型判断

                            } else {
                                setValue(Resource.error(it.body.message, processResponse(it)))
                                onFetchFailed()
                            }
                        } else {
                            setValue(
                                Resource.error(
                                    if (it?.body is RealResponseBody<*>) {
                                        it.body.message
                                    } else null, processResponse(it)
                                )
                            )
                            onFetchFailed()
                        }
                    }
                }
            } else {
                DebugLog.e("NetworkBoundResource->onChanged->Http状态错误:${it?.code} .. ${it?.errorMessage} \n${it?.body}")
                setValue(Resource.error(it?.errorMessage, processResponse(it)))
                onFetchFailed()
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    @Suppress("UNCHECKED_CAST")
    @WorkerThread
    protected fun processResponse(response: ApiResponse<RequestType>?): ResultType? {
        return if (response?.body is RealResponseBody<*>) {
            response.body.data as ResultType?
        } else null
    }

    /**
     * 调用它来创建API调用。
     *
     * @return 从网络接口获得的数据
     */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    /**
     * 获取失败时调用。子类可能需要重置组件。
     * 类似rate limiter(速率限制器)。
     */
    protected abstract fun onFetchFailed()

    /**
     * 获取成功后调用。子类可能需要获得服务器返回数据做保存之类的处理。
     */
    protected abstract fun onFetchSuccess(item: ResultType?)

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}