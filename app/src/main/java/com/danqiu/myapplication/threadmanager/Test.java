package com.danqiu.myapplication.threadmanager;

import java.io.File;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/12
 */
public class Test {
    private void threadPoolTest(){
        ThreadPoolManager.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                //执行内容
            }
        });

    }



}
