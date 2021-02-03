package com.dsl.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author dsl-abben
 * on 2020/02/19.
 */
object BeanUtil {
    /**
     * 将bean类转换为带token的Map
     *
     * @param bean 自定义bean类
     * @return 带token的Map
     */
    fun beanToMap(bean: Any): MutableMap<String, Any> {
        val gson = Gson()
        return gson.fromJson(
            gson.toJson(bean),
            object : TypeToken<MutableMap<String, Any>>() {}.type
        )
    }
}
