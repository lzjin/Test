package com.danqiu.myapplication.model;

import com.danqiu.myapplication.retrofit.ApiService;
import com.danqiu.myapplication.retrofit.RetrofitClientManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/27.
 *
 */

public class BaseModel implements IModel{

    public ApiService apiService= RetrofitClientManager.getInstance().apiService;

    /**
     * RxJava线程调度
     * Schedulers.io I/O操作，比如文件操作，网络操作等，他和newThread有点类似
     * Schedulers.immediate 作用于当前线程运行，相当于你在哪个线程执行代码就在哪个线程运行
     * Schedulers.newthread 运行在新线程中，相当于new Thread()，每次执行都会在新线程中
     * Schedulers.computation 一些需要CPU计算的操作，比如图形，视频等
     * AndroidSchedulers.mainThread 指定运行在Android主线程中
     * @return
     */
    public Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())//指定发射事件时的线程
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());//指定订阅者接收事件时的线程。
            }
        };
    }
    public Observable.Transformer schedulersTransformerIo() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())//指定发射事件时的线程
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());//指定订阅者接收事件时的线程。
            }
        };
    }

}
