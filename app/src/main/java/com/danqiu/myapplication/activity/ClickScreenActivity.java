package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.danqiu.myapplication.accessibility.AccessiblityMian;
import com.danqiu.myapplication.utils.IntentUtil;
import com.danqiu.myapplication.utils.MLog;

import java.io.IOException;

/**
 * Created by Administrator on 2018/11/16.
 * https://github.com/PopFisher/AccessibilitySample
 */

public class ClickScreenActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_screen);
       // initView();
        IntentUtil.IntenToActivity(this,AccessiblityMian.class);
    }

    private void initView() {
        final int width = getWindowManager().getDefaultDisplay().getWidth();
        final int height = getWindowManager().getDefaultDisplay().getHeight();

        //每10s产生一次点击事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                clickMethod(width,height);
            }
        }).start();
    }

    /**
     * 点击实现
     */
    private void clickMethod(int xW,int yH) {
        while (true) {
            //生成点击坐标
            int x = (int) (Math.random() * xW * 0.6 + xW * 0.2);
            int y = (int) (Math.random() * yH * 0.6 + yH * 0.2);
            //利用ProcessBuilder执行shell命令
            String[] order = {
                    "input",
                    "tap",
                    "" + x,
                    "" + y
            };
            try {
                   new ProcessBuilder(order).start();
                   Thread.sleep(5*1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印点击的点的坐标
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Toast.makeText(this, "X =" + x + ";Y =" + y, Toast.LENGTH_SHORT).show();

        MLog.i("log","------------自动点击----（"+x+","+y+")");

        return true;
    }


}
