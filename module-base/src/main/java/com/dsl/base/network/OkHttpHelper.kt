package com.dsl.base.network

import com.dsl.network.interceptor.logging.LogInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException
import kotlin.jvm.Throws

/**
 * @author dsl-abben
 * on 2020/02/14.
 */
object OkHttpHelper {
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

    @Volatile
    private var okHttpClient: OkHttpClient? = null

    @Suppress("DEPRECATION")
    fun getOkHttpClient(): OkHttpClient {
        return okHttpClient ?: synchronized(this) {
            val sslContext = SSLContext.getInstance("TLS")
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
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

            okHttpClient ?: OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(LogInterceptor())
                .sslSocketFactory(sslContext.socketFactory)
                .hostnameVerifier { _, _ ->
                    true
                }
                .build()
        }
    }
}
