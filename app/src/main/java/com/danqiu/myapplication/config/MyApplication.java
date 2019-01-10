package com.danqiu.myapplication.config;

import android.app.Application;
import android.content.Context;

import com.danqiu.myapplication.fresco.ImageLoaderConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2018/9/26.
 *
 */

public class MyApplication extends Application{
    private static RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
       // Fresco.initialize(this);
        //配置缓存的初始化
        Fresco.initialize(this, ImageLoaderConfig.getImagePipelineConfig(this));

        refWatcher= setupLeakCanary();

        //内存检查
        if (LeakCanary.isInAnalyzerProcess(this)) {//1
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        else {
            LeakCanary.install(this);
        }

    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static void getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        RefWatcher refWatcher=leakApplication.refWatcher;

        refWatcher.watch(refWatcher);
    }



}
