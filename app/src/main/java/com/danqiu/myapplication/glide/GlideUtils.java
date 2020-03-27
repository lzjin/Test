package com.danqiu.myapplication.glide;

import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.danqiu.myapplication.R;

import java.io.File;
import java.math.BigDecimal;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by lzj on 2020/3/27
 * Describe ：封装了一个简单的Glide工具类
 */
public class GlideUtils {
    /**
     * 普通加载 无缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .skipMemoryCache(true)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);
    }
    /**
     * 普通加载  缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageCache(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .skipMemoryCache(false)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);
    }

    /**
     * 圆图加载 无缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .transform(new CircleCrop())  // 圆角图片
                .skipMemoryCache(true)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);
    }
    /**
     * 圆图加载  缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircleCache(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .transform(new CircleCrop())  // 圆角图片
                .skipMemoryCache(false)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);
    }

    /**
     * 圆角图片加载  无缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageCorners(Context context, String url,int  cornersSize,ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .transform(new RoundedCorners(cornersSize)) //
                .skipMemoryCache(true)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);

    }

    /**
     * 圆角图片加载  缓存
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageCornersCache(Context context, String url,int  cornersSize,ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .transform(new RoundedCorners(cornersSize)) //
                .skipMemoryCache(false)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);
    }

    /**
     * 指定圆角图片加载  无缓存
     * @param context
     * @param url
     * @param cornersSize
     * @param cornerType 如顶部 RoundedCornersTransformation.CornerType.TOP
     * @param imageView
     */
    public static void loadImageCorners(Context context, String url, int  cornersSize, RoundedCornersTransformation.CornerType cornerType, ImageView imageView){
        //或
//        RequestOptions requestOption = RequestOptions.bitmapTransform(new
//                RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.TOP));
//        RequestOptions requestOption2 = RequestOptions.centerCropTransform().transform(new
//                RoundedCornersTransformation(20,0, RoundedCornersTransformation.CornerType.TOP));

        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                //.apply(requestOption)
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .transform(new RoundedCornersTransformation(cornersSize, 0,cornerType))  // 圆角图片
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .skipMemoryCache(true)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);

    }

    /**
     * 指定圆角图片加载  缓存
     * @param context
     * @param url
     * @param cornersSize
     * @param cornerType 如顶部 RoundedCornersTransformation.CornerType.TOP
     * @param imageView
     */
    public static void loadImageCornersCache(Context context, String url, int  cornersSize, RoundedCornersTransformation.CornerType cornerType, ImageView imageView){
        //或
//        RequestOptions requestOption = RequestOptions.bitmapTransform(new
//                RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.TOP));
//        RequestOptions requestOption2 = RequestOptions.centerCropTransform().transform(new
//                RoundedCornersTransformation(20,0, RoundedCornersTransformation.CornerType.TOP));

        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                //.apply(requestOption)
                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
                .transform(new RoundedCornersTransformation(cornersSize, 0,cornerType))  // 圆角图片
                .placeholder(R.mipmap.ic_launcher_round)// 加载中图片
                .error(R.mipmap.ic_launcher_round) // 加载失败图片
                .skipMemoryCache(true)//关闭内存缓存
                .diskCacheStrategy( DiskCacheStrategy.AUTOMATIC )//设置磁盘缓存
                .into(imageView);
    }

    /**
     *  加载本地resId
     * @param mContext
     * @param resId
     * @param imageview
     * @param defaultImg
     */
    public static void loadImageLocal(Context mContext, Integer resId, ImageView imageview, int defaultImg) {
        GlideApp.with(mContext)
                .load(resId)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .into(imageview);
    }
    /**
     *  加载本地SD图片
     * @param mContext
     * @param path
     * @param imageview
     * @param defaultImg
     */
    public static void loadImageSD(Context mContext, String path, ImageView imageview, int defaultImg) {
        GlideApp.with(mContext)
                .load(path)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .into(imageview);
    }
    /**
     *  加载本地  Uri
     * @param mContext
     * @param uri
     * @param imageview
     * @param defaultImg
     */
    public static void LoadImageUri(Context mContext, Uri uri, ImageView imageview, int defaultImg) {
        GlideApp.with(mContext)
                .load(uri)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
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
                .into(imageview);
    }

    /**
     * 清理内存  在UI主线程中进行
     * @param context
     */
    public static boolean  clearMemoryCache(Context context){
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                GlideApp.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 清理磁盘缓存 必须在子线程中执行
     * @param context
     */
    public static boolean clearFileCache(Context context, CacheCallback callback){
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlideApp.get(context).clearDiskCache();
                        if(callback!=null){
                            callback.onCacheCallback();
                        }
                    }
                }).start();
            }else {
                GlideApp.get(context).clearDiskCache();
                if(callback!=null){
                    callback.onCacheCallback();
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static interface CacheCallback {
        void onCacheCallback();
    }
    /**
     * 获取Glide磁盘缓存大小
     * @param mContext
     * @return
     */
    public static String getCacheSize(Context mContext) {
        try {
            return getFormatSize(getFolderSize(new File(mContext.getCacheDir() + "/GlideCacheFolder" )));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    // 获取指定文件夹内所有文件大小的和
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }



}
