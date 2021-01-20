package com.dsl.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class GlideImageLoaderUtil {

    public static void loadImageWithString(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void loadImageWithString(Context context, String url, @DrawableRes int placeholderResourceId,
                                           ImageView imageView
    ) {
        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(placeholderResourceId))
                .into(imageView);
    }

    public static void loadImageWithString(Context context, String url,
                                           @DrawableRes int placeholderResourceId, @DrawableRes int errorResourceId,
                                           ImageView imageView
    ) {
        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(placeholderResourceId).error(errorResourceId))
                .into(imageView);
    }

    public static void loadImageWithString(Context context, String url, @DrawableRes int placeholderResourceId,
                                           @DrawableRes int errorResourceId, int width, int height, ImageView imageView
    ) {
        Glide.with(context).load(url)
                .apply(new RequestOptions().placeholder(placeholderResourceId).error(errorResourceId).override(width, height))
                .into(imageView);
    }

    public static void loadImageWithResourceId(Context context, @DrawableRes Integer resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).into(imageView);
    }

    public static void loadImageWithResourceId(Context context, @DrawableRes Integer resourceId,
                                               @DrawableRes int placeholderResourceId, ImageView imageView
    ) {
        Glide.with(context).load(resourceId)
                .apply(new RequestOptions().placeholder(placeholderResourceId))
                .into(imageView);
    }


    public static void loadImageWithResourceId(Context context, @DrawableRes Integer resourceId,
                                               @DrawableRes int placeholderResourceId, @DrawableRes int errorResourceId,
                                               ImageView imageView
    ) {
        Glide.with(context).load(resourceId)
                .apply(new RequestOptions().placeholder(placeholderResourceId).error(errorResourceId))
                .into(imageView);
    }


    public static void loadImageWithFile(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).into(imageView);
    }

    public static void loadImageWithFile(Context context, File file, @DrawableRes int placeholderResourceId, ImageView imageView) {
        Glide.with(context).load(file)
                .apply(new RequestOptions().placeholder(placeholderResourceId))
                .into(imageView);
    }

    public static void loadImageWithFile(Context context, File file, @DrawableRes int placeholderResourceId,
                                         @DrawableRes int errorResourceId, ImageView imageView
    ) {
        Glide.with(context).load(file)
                .apply(new RequestOptions().placeholder(placeholderResourceId).error(errorResourceId))
                .into(imageView);
    }

    public static void loadImageWithUri(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    public static void loadImageWithUri(Context context, Uri uri, @DrawableRes int placeholderResourceId, ImageView imageView) {
        Glide.with(context).load(uri)
                .apply(new RequestOptions().placeholder(placeholderResourceId))
                .into(imageView);
    }

    public static void loadImageWithUri(Context context, Uri uri, @DrawableRes int placeholderResourceId,
                                        @DrawableRes int errorResourceId, ImageView imageView) {
        Glide.with(context).load(uri)
                .apply(new RequestOptions().placeholder(placeholderResourceId).error(errorResourceId))
                .into(imageView);
    }

    public static void loadImageWithString(Context context, String url, ImageView imageView, boolean skipMemoryCache) {
        Glide.with(context).load(url).apply(
                new RequestOptions().skipMemoryCache(skipMemoryCache).diskCacheStrategy(DiskCacheStrategy.NONE)
        ).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadImageWithStringCircle(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadImageWithStringCircle(Context context, String url, @DrawableRes int placeholderResourceId,
                                                 @DrawableRes int errorResourceId, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(placeholderResourceId).error(errorResourceId))
                .into(imageView);
    }

    public static void loadImageWithStringOverride(Context context, String url, @IntRange(from = 0) int size, ImageView imageView) {
        Glide.with(context).load(url).apply(RequestOptions.overrideOf(size))
                .into(imageView);
    }
}
