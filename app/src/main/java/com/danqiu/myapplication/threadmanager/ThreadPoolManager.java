package com.danqiu.myapplication.threadmanager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: 线程池管理
 * Author: Administrator
 * CreateDate: 2020/4/12
 */
public class ThreadPoolManager {
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    //FIFO 线程安全的
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //将请求添加到队列中
    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.add(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            //拒绝策略
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);//r异常重新排队或移除
            }
        });
        mThreadPoolExecutor.execute(coreThread);
    }

    //创建核心线程
    public Runnable coreThread = new Runnable() {
        Runnable runnable = null;
        @Override
        public void run() {
            while (true) {
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


}
