package com.dsl.util

import android.os.Environment
import java.io.File

/**
 * 构造文件下载路径
 *
 * @author dsl-abben
 * on 2020/02/24.
 */
class SavePathBuilder(
    /**
     *功能模块
     */
    functionalModule: String,
    /**
     * 功能模块后续子目录，可以为null
     */
    subdirectory: String?
) {

    companion object {
        const val PICTURE_FUNCTIONAL_MODULE = "pictures"

        const val IM_FUNCTIONAL_MODULE = "im"

        /***
         * 功能模块后续子目录,pictures/compress/  压缩后保存目录
         */
        const val COMPRESS_SUBDIRECTORY = "compress"
    }

    /**
     * 第一级文件夹
     */
    private val firstDirectory: String = "DslDoctorPlus"

    var savePath: String = ""

    init {
        // 本地基本路径
        val basicPath =
            Environment.getExternalStorageDirectory().absolutePath + File.separatorChar + firstDirectory
        savePath = setSavePath(basicPath, functionalModule, subdirectory)
        // 判断文件夹是否存在，不存在就新建
        val file = File(savePath)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    private fun setSavePath(
        masterDirectory: String,
        functionalModule: String,
        subdirectory: String?
    ): String {
        val stringBuilder = StringBuilder(masterDirectory)
        stringBuilder.append(File.separatorChar).append(functionalModule)
        subdirectory?.let { stringBuilder.append(File.separatorChar).append(it) }
        stringBuilder.append(File.separatorChar)
        return stringBuilder.toString()
    }

    /**
     * 为IM专设方法
     */
    fun getAbsolute(): String {
        val abs = StringBuilder(savePath)
        abs.deleteCharAt(abs.length - 1)
        val imlocalPath = savePath + "image/download" + File.separatorChar
        val file = File(imlocalPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        // 语音文件
        val recordPath = savePath + "record" + File.separatorChar
        val recordFile = File(recordPath)
        if (!recordFile.exists()) {
            recordFile.mkdirs()
        }
        return abs.toString()
    }
}
