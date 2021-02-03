package com.dsl.network

import com.dsl.util.DebugLog
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.*

/**
 * @author dsl-abben
 * on 2020/02/18.
 */
class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (ApiManager.showDebug) {
            val requestBody = request.body()
            var requestBodyString: String? = null
            if (requestBody != null) {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                var charset = Charset.forName("UTF-8")
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(Charset.forName("UTF-8"))
                }
                requestBodyString = buffer.readString(charset)
            }
            DebugLog.i(
                String.format(
                    Locale.getDefault(),
                    "请求method:%s\n请求url：%s",
                    request.method(),
                    request.url()
                )
            )
            requestBodyString?.let {
                val segmentSize = 3 * 1024
                val length = it.length
                if (length <= segmentSize) {
                    // 长度小于等于限制直接打印
                    DebugLog.i(String.format("请求body：%s", it))
                } else {
                    var msg = it
                    while (msg.length > segmentSize) {
                        // 循环分段打印日志
                        val logContent = msg.substring(0, segmentSize)
                        msg = msg.replace(logContent, "")
                        DebugLog.i(String.format("请求body：%s", logContent))
                    }
                    DebugLog.i(String.format("%s", it))
                }
            }
        }
        return chain.proceed(request)
    }
}
