package com.danqiu.myapplication.config;

import android.app.Application;

import com.danqiu.myapplication.fresco.ImageLoaderConfig;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2018/9/26.
 *
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
       // Fresco.initialize(this);
        //配置缓存的初始化
        Fresco.initialize(this, ImageLoaderConfig.getImagePipelineConfig(this));
    }


}
