package com.dsl.extend

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dsl.base.R

/**
 * ImageView的拓展函数
 *
 * @author dsl-abben
 * on 2020/02/21.
 */

/**
 * 加载图片
 */
fun ImageView.loadImageExtend(context: Context, path: String) {
    Glide.with(context).load(path).into(this)
}

/**
 * 加载图片带占位图
 */
fun ImageView.loadImageExtendWithPlaceholder(
    context: Context,
    path: String,
    @DrawableRes resourceId: Int = R.mipmap.icon_default_protrait
) {
    Glide.with(context).load(path).apply(RequestOptions.placeholderOf(resourceId)).into(this)
}

/**
 * 加载图片跳过内存缓存
 */
fun ImageView.loadImageExtendNonCache(context: Context, path: String) {
    Glide.with(context).load(path)
        .apply(RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.NONE))
        .into(this)
}

/**
 * 加载圆形图片
 */
fun ImageView.loadImageCircleCropExtend(context: Context, path: String) {
    Glide.with(context).load(path)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}
