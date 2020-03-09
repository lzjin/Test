package com.danqiu.myapplication.config;

import android.os.Environment;

/**
 * Created by Administrator on 2018/9/28.
 */

public class Constants {
    public static final String SOFT_FILE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/AA_Test";//根路径
    public static final String HEAD_IMA_PATH=SOFT_FILE_PATH+"/Img/";//头像路径
    public static final String HEAD_DOWN_NAME=SOFT_FILE_PATH+"/download/";//下载路径
    public static final String APP_FILE_NAME_APK="test.apk";//apk文件名
    public static final String CAMERA_IMA_PATH=Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/";//保存到相机
}
