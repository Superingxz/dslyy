package com.dsl.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import kotlin.jvm.Throws

/**
 * gson反序列化拦截,防止后端天才们把原本对应key的value应当num,0的，但为空时候竟然返回空字符串""的神奇操作
 * @author dsl-abben
 * on 2020/03/27.
 */
class IntSecurityAdapter : TypeAdapter<Int?>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: Int?) {
        try {
            var tempValue = value
            if (tempValue == null) {
                tempValue = -1
            }
            out?.value(tempValue)
        } catch (e: Exception) {
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Int? {
        var value: Int?
        try {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                value = -1
            } else if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                value = if (b) 1 else 0
            } else if (`in`.peek() == JsonToken.STRING) {
                try {
                    value = `in`.nextString().toInt()
                } catch (e: Exception) {
                    value = -1
                }
            } else {
                value = `in`.nextInt()
            }
        } catch (e: Exception) {
            value = -1
        }
        return value
    }
}
