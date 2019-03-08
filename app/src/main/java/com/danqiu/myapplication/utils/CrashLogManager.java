package com.danqiu.myapplication.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzj on 2019/3/8
 * Describe ：手写崩溃日志管理(用于记录未捕获异常，上传服务器分析。)
 * 第三方:友盟、腾讯...
 */
public class CrashLogManager implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    public static final boolean DEBUG=true;
    //日志保存路径
    public static final String PATH=Environment.getExternalStorageDirectory().getPath()+"/TestApp/CrashLog/";
    public static final String FILE_NAME="carsh_";
    public static final String FILE_NAME_SUFFIX=".txt";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashLogManager实例
    private static CrashLogManager INSTANCE = new CrashLogManager();
    //程序的Context对象
    private Context mContext;

    /** 保证只有一个CrashLogManager实例 */
    private CrashLogManager() {
    }

    /** 获取CrashLogManager实例 ,单例模式 */
    public static CrashLogManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashLogManager为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 最关键函数，当程序有未捕获的异常时，系统自动调用次方法
     * @param thread 未捕获异常线程
     * @param ex     未捕获异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //异常信息写入sd卡
            dumpExceptionToSD(ex);
            //异常信息上传服务器
            uploadExcptionToServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
    /**
     * 异常信息写入sd卡
     * @param ex
     */
    private void dumpExceptionToSD(Throwable ex) throws IOException{
        //无sd卡或无法使用
       if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
           if(DEBUG){
               Log.w(TAG,"No SD card");
           }
       }
       else {
           //创建文件夹
           File dir=new File(PATH);
           if(!dir.exists()){
               dir.mkdirs();
           }
           long currentData=System.currentTimeMillis();
           String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentData));
           File file =new File(PATH + FILE_NAME + time.replace(" ", "_") + FILE_NAME_SUFFIX );
           Log.e(TAG,"crash file path:"+file.getAbsolutePath());
           try {
               PrintWriter printWriter=new PrintWriter(new BufferedWriter(new FileWriter(file)));
               printWriter.println(time);//写入时间
               phoneInformation(printWriter);//写入手机信息
               printWriter.println();
               ex.printStackTrace(printWriter);//异常信息
               printWriter.close();
           } catch (IOException e) {
               e.printStackTrace();
               Log.e(TAG,"writer crash log failed");
           } catch (PackageManager.NameNotFoundException e) {
               e.printStackTrace();
           }
       }
    }
    /**
     * phone info
     */
    private void phoneInformation(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm=mContext.getPackageManager();
        PackageInfo pi=   pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
        pw.println("App Version: "+pi.versionName+"_versionCode:"+pi.versionCode);
        pw.println("OS Version: "+ Build.VERSION.RELEASE+"_SDK:"+Build.VERSION.SDK_INT);
        pw.println("Vendor: "+Build.MANUFACTURER);
        pw.println("Model: "+Build.MODEL);
        pw.println("CPU ABI: "+Build.CPU_ABI);

    }
    /**
     * 异常信息上传服务器
     */
    private void uploadExcptionToServer() {
        //上传you  web server
    }

}
