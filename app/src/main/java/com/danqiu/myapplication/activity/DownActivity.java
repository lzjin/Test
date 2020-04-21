package com.danqiu.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 文件下载
 * Author: Lzj
 * CreateDate: 2020/4/21
 */
public class DownActivity extends AppCompatActivity {
    long taskId;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        ButterKnife.bind(this);
        //注册
        Aria.download(this).register();


    }
    @OnClick(R.id.btn)
    public void onViewClicked() {
        initView();
    }

    private void initView() {
        taskId = Aria.download(this)
                .load("http://www.danqiuedu.com/dqonline.apk")     //读取下载地址
                .setFilePath(Constants.HEAD_DOWN_NAME + "test_"+System.currentTimeMillis()+".apk") //设置文件保存的完整路径
                .resetState()
                .create();   //创建并启动下载
    }


    //在这里处理任务执行中的状态，如进度进度条的刷新
    @SuppressLint("NewApi")
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        if (task.getKey().equals("http://www.danqiuedu.com/dqonline.apk")) {
            //同一个url任务  ...
        }
        int p = task.getPercent();    //任务进度百分比
        // String speed = task.getConvertSpeed();	//转换单位后的下载速度，单位转换需要在配置文件中打开
        //  String speed1 = task.getSpeed(); //原始byte长度速度
        MLog.i("test", "---------下载进度------" + p);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(p,true);
            }
        });


    }

    @SuppressLint("NewApi")
    @Download.onTaskComplete
    public void taskComplete(DownloadTask task) {
        progressBar.setProgress(100,true);
        //在这里处理任务完成的状态
        ToastUtil.showLong(this, "下载完成");
    }


}
