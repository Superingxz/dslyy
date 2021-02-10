package com.dsl.base.network

import com.dsl.base.BuildConfig
import com.dsl.base.appContext
import com.dsl.network.*
import com.dsl.network.interceptor.CacheInterceptor
import com.dsl.network.interceptor.logging.LogInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 网络请求构建器，继承BasenetworkApi 并实现setHttpClientBuilder/setRetrofitBuilder方法，
 * 在这里可以添加拦截器，设置构造器可以对Builder做任意操作
 */

// 双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
val apiService: PlusApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(PlusApi::class.java, BuildConfig.BASE_URL)
}

// 测试的
val apiService1: PlusApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(PlusApi::class.java, BuildConfig.BASE_URL)
}

fun <T> getService(service: Class<T>): T {
    return NetworkApi.INSTANCE.getApi(service, BuildConfig.BASE_URL)
}

const val showDebug = true

class NetworkApi : BaseNetworkApi() {

    companion object {
        /**
         * 连接超时
         */
        private const val CONNECT_TIMEOUT: Long = 30

        /**
         * 读取超时
         */
        private const val READ_TIMEOUT: Long = 25

        /**
         * 写入超时
         */
        private const val WRITE_TIMEOUT: Long = 25
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val sslContext = SSLContext.getInstance("TLS")
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        builder.apply {
            // 设置缓存配置 缓存最大10M
            cache(Cache(File(appContext.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            // 添加Cookies自动持久化
            cookieJar(cookieJar)
            // 示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(MyHeadInterceptor())
            addInterceptor(HeaderInterceptor())
            // 添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(LogInterceptor())
            // 超时时间 连接、读、写
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            sslSocketFactory(sslContext.socketFactory)
            hostnameVerifier { _, _ ->
                true
            }
        }
        return builder
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addCallAdapterFactory(LiveDataCallAdapterFactory())
            addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().enableComplexMapKeySerialization()
                        .serializeNulls()
                        .registerTypeAdapter(
                            Long::class.java,
                            LongSecurityAdapter()
                        )
                        .registerTypeAdapter(
                            Int::class.java,
                            IntSecurityAdapter()
                        )
                        .registerTypeAdapter(
                            Float::class.java,
                            FloatSecurityAdapter()
                        )
                        .registerTypeAdapter(
                            List::class.java,
                            ArraySecurityAdapter()
                        ).create()
                )
            )
        }
    }

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }
}
