package com.dsl.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期时间格式转换工具
 * @author dsl-abben
 * on 2020/03/03.
 */
object DateTimeUtil {

    /**
     * 格式化日期 从左至右分别为-年-月-日 时:分:秒.毫秒 {yyyy-MM-dd HH:mm:ss.fff}:使用24小时制格式化日期
     */
    fun formatDate(datetime: String, format: String = "yyyy-MM-dd"): String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return try {
            val date = dateFormat.parse(datetime)
            dateFormat.format(date)
        } catch (e: ParseException) {
            datetime
        }
    }

    /**
     * 获得当前时间
     *
     * @param pattern 时间格式,可以为"yyyy-MM-dd HH:mm:ss"
     * @return 当前时间
     */
    fun getNowTime(pattern: String = "yyyy-MM-dd"): String {
        val now = Date()
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(now)
    }

    /**
     * 返回格式化的时间
     *
     * @param timeInMillis 要格式化的时间戳
     * @param pattern      时间格式,可以为"yyyy-MM-dd HH:mm:ss"
     */
    fun getFormtTime(timeInMillis: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        val date = Date(timeInMillis)
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(date)
    }

    /**
     * 获得当前时间戳,毫秒级
     */
    fun getUnixTime(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString 要转换的字符串
     * @param pattern    时间格式,可以为"yyyy-MM-dd HH:mm:ss"
     * @return 时间戳
     */
    fun getStringToUnixTime(dateString: String, pattern: String): Long {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        var date = Date()
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.time
    }

    /**
     * 根据秒数获得格式化后的字符串，形如"00:12:54"
     */
    fun fetFormatString(seconds: Long): String {
        val hour = seconds / 3600
        var minutes = seconds % 3600
        val second = minutes % 60
        minutes /= 60
        return String.format("%02d:%02d:%02d", hour, minutes, second)
    }

    fun getFormatCalendar(dateStr: String, pattern: String = "yyyy-MM-dd"): Calendar {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = dateFormat.parse(dateStr)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }
}
