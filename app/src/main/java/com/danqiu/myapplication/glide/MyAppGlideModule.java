package com.danqiu.myapplication.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by lzj on 2020/3/27
 * Describe ：自定义Glide的配置
 * LruCache（二级内存缓存）、DiskLruCache（三级磁盘缓存）
 * 配置完之后Build项目生成GlideApp类
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置缓存大小为20mb
        int memoryLruCacheSizeBytes = 1024 * 1024 * 20;
        int memoryDiskLruCacheSizeBytes = 1024 * 1024 * 200;
        //二级内存缓存
        builder.setMemoryCache(new LruResourceCache(memoryLruCacheSizeBytes));
        //三级磁盘缓存
        builder.setDiskCache(new DiskLruCacheFactory(context.getCacheDir().getPath() + "/GlideCacheFolder", memoryDiskLruCacheSizeBytes));
        Log.e("test","Glide的内存="+memoryLruCacheSizeBytes +",磁盘="+memoryDiskLruCacheSizeBytes);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
