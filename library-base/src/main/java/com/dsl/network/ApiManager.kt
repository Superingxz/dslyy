package com.dsl.network

import com.dsl.base.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author dsl-abben
 * on 2020/02/14.
 */
object ApiManager {

    const val showDebug = true

    var baseUrl = BuildConfig.BASE_URL

    @Volatile
    private var mRetrofit: Retrofit? = null

    @Volatile
    private var plusApi: PlusApi? = null

    private fun getRetrofit(): Retrofit {
        return mRetrofit ?: synchronized(this) {
            mRetrofit ?: Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpHelper.getOkHttpClient())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .enableComplexMapKeySerialization()
                            .serializeNulls()
                            .registerTypeAdapter(Long::class.java, LongSecurityAdapter())
                            .registerTypeAdapter(Int::class.java, IntSecurityAdapter())
                            .registerTypeAdapter(Float::class.java, FloatSecurityAdapter())
                            .registerTypeAdapter(
                                List::class.java,
                                ArraySecurityAdapter()
                            )
                            .create()
                    )
                )
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
        }
    }

    fun getPlusApi(): PlusApi {
        return plusApi ?: synchronized(this) {
            plusApi ?: getRetrofit().create(PlusApi::class.java)
        }
    }

    fun <T> getService(service: Class<T>): T {
        return getRetrofit().create(service) as T
    }
}
