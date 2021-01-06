package com.dsl.network.vo

/**
 * 上传文件返回的Bean
 * @author dsl-abben
 * on 2020/02/24.
 */
data class UploadFileResponseData(
    val fileList: List<FileListItem>
)

data class FileListItem(
    val pic_id: String,
    val pic_url: String
)