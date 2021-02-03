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
 * on 2020/06/17.
 */
class FloatSecurityAdapter : TypeAdapter<Float?>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: Float?) {
        try {
            var tempValue = value
            if (tempValue == null) {
                tempValue = 0F
            }
            out?.value(tempValue)
        } catch (e: Exception) {
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Float? {
        var value: Float?
        try {
            value = if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                0F
            } else if (`in`.peek() == JsonToken.BOOLEAN) {
                val b = `in`.nextBoolean()
                if (b) 1F else 0F
            } else if (`in`.peek() == JsonToken.STRING) {
                try {
                    `in`.nextString().toFloat()
                } catch (e: Exception) {
                    0F
                }
            } else {
                `in`.nextDouble().toFloat()
            }
        } catch (e: Exception) {
            value = 0F
        }
        return value
    }
}
