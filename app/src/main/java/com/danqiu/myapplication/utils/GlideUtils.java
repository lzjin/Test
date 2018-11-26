package com.danqiu.myapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Administrator
 *   Glide的图片加载框架 工具类
 */
public class GlideUtils {

    /**
     *  说明: 圆形图片加载  带边框
     * @param mContext
     * @param url
     * @param imageview
     * @param defaultImg 默认图片
     * @param frameSize 带边框 大小size
     * @param mColor  边框颜色
     */
    public static void LoadCircleImageFrame(Context mContext, String url, ImageView imageview, int defaultImg, int frameSize, int mColor) {
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
             //   .transform(new GlideCircleTransform(mContext,frameSize,mContext.getResources().getColor(mColor)))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }
    /**
     *  说明: 图片加载   本地或者网络
     * @param mContext
     * @param url
     * @param imageview
     * @param defaultImg 默认图片
     */
    public static void LoadImage(Context mContext, String url, ImageView imageview, int defaultImg) {
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }
    /**
     *  说明: 圆形图片加载   本地或者网络
     * @param mContext
     * @param url
     * @param imageview
     * @param defaultImg 默认图片
     */
    public static void LoadCircleImage(Context mContext, String url, ImageView imageview, int defaultImg) {
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                //.transform(new GlideCircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }
    /**
     *  加载网络图片  自定义图片(如圆形)
     * @param mContext
     * @param path
     * @param imageCustom
     * @param defaultImg
     */
    public static void LoadCustomImage(Context mContext, Integer path, View imageCustom, int defaultImg) {
        Glide.with(mContext)
                .load(path)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into((ImageView) imageCustom);
    }

    /**
     *  加载本地图片
     * @param mContext
     * @param resId
     * @param imageview
     * @param defaultImg
     */
    public static void LoadImageLocal(Context mContext, Integer resId, ImageView imageview, int defaultImg) {
        Glide.with(mContext)
                .load(resId)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }
    /**
     *  加载本地图片
     * @param mContext
     * @param path
     * @param imageview
     * @param defaultImg
     */
    public static void LoadImageSD(Context mContext, String path, ImageView imageview, int defaultImg) {
        Glide.with(mContext)
                .load(path)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }
    /**
     *  加载本地图片
     * @param mContext
     * @param uri
     * @param imageview
     * @param defaultImg
     */
    public static void LoadImageUri(Context mContext, Uri uri, ImageView imageview, int defaultImg) {
        Glide.with(mContext)
                .load(uri)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageview);
    }

    /**
     * 加载本地带尺寸的图片
     */
    public static void LoadImageWithSize(Context mContext, String path, int Width, int Height, int defaultImg, ImageView imageview) {
        Glide.with(mContext)
                .load(path)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .override(Width, Height)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageview);
    }

}
