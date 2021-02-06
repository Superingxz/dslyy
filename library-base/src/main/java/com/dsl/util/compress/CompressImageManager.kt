package com.dsl.util.compress

import com.dsl.base.BaseApp
import com.dsl.extend.util.logd
import com.dsl.extend.util.logi
import com.dsl.util.SavePathBuilder
import com.dsl.util.compress.internal.PictureCompressor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.IOException

/**
 * @author dsl-abben
 * on 2020/02/24.
 */
class CompressImageManager {

    companion object {
        @Volatile
        private var instance: CompressImageManager? = null

        fun createCompressImageManager(): CompressImageManager {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: CompressImageManager()
                }
        }
    }

    interface CompressImageObserver {
        fun onSuccess(compressFiles: List<UploadFileBean>)

        fun onError(e: Throwable?)
    }

    /**
     * 预处理操作、压缩JPG
     */
    fun startCompressImage(
        uploadFileDisplayBeans: List<String>,
        compositeDisposable: CompositeDisposable?,
        compressImageObserver: CompressImageObserver
    ) {
        Observable.just(uploadFileDisplayBeans)
            .flatMap { t ->
                val uploadFileBeans = mutableListOf<UploadFileBean>()
                for (item in t) {
                    // 压缩后返回
                    uploadFileBeans.add(compressImageAndSave(item))
                }
                Observable.just(uploadFileBeans)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<UploadFileBean>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable?.add(d)
                }

                override fun onNext(t: List<UploadFileBean>?) {
                    t?.let { compressImageObserver.onSuccess(it) }
                }

                override fun onError(e: Throwable?) {
                    compressImageObserver.onError(e)
                }
            })
    }

    /**
     * 压缩图片并返回压缩后本地路径
     *
     * @param wantCompressImagePath 待压缩的图片路径
     * @return 压缩后本地路径
     */
    private fun compressImageAndSave(wantCompressImagePath: String): UploadFileBean {
        val fileBean = UploadFileBean("")
        val file = File(wantCompressImagePath)
        val saveDirectoryPath = SavePathBuilder(
            SavePathBuilder.PICTURE_FUNCTIONAL_MODULE,
            SavePathBuilder.COMPRESS_SUBDIRECTORY
        ).savePath
        try {
            val compressFIle =
                PictureCompressor.with(BaseApp.getInstance().applicationContext)
                    .savePath(saveDirectoryPath)
                    .load(file)
                    .get()
            if (compressFIle != null) {
                "CompressImageManager->compressImageAndSave压缩图片成功:${compressFIle.absolutePath}".logi()
                fileBean.localFilePath = compressFIle.absolutePath
            }
        } catch (e: IOException) {
            ("CompressImageManager->compressImageAndSave压缩图片失败:$e").logd()
            fileBean.localFilePath = wantCompressImagePath
            e.printStackTrace()
        }
        return fileBean
    }
}
