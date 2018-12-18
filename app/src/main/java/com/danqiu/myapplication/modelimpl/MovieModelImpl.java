package com.danqiu.myapplication.modelimpl;

import android.app.ProgressDialog;
import android.content.Context;

import com.danqiu.myapplication.bean.BaseHttpResult;
import com.danqiu.myapplication.bean.BaseInfo;
import com.danqiu.myapplication.bean.LoginBean;
import com.danqiu.myapplication.model.IMovieModel;
import com.danqiu.myapplication.retrofit.ApiService;
import com.danqiu.myapplication.retrofit.ProgressSubscriber;
import com.danqiu.myapplication.retrofit.RetrofitClientManager;
import com.danqiu.myapplication.views.ProgressDialogFragment;

import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/26.
 *
 */
@SuppressWarnings("unchecked")
public class MovieModelImpl implements IMovieModel{
    private Context mContext;
    private ProgressDialog dialog;
    private ProgressDialogFragment dialogFragment;
    public ApiService apiService=RetrofitClientManager.getInstance().apiService;
    public MovieModelImpl(Context mContext, ProgressDialog dialog) {
        this.mContext = mContext;
        this.dialog=dialog;
    }

    /**
     *  通用 RxJava线程调度
     *
     * Schedulers.io I/O操作，比如文件操作，网络操作等，他和newThread有点类似
     * Schedulers.immediate 作用于当前线程运行，相当于你在哪个线程执行代码就在哪个线程运行
     * Schedulers.newthread 运行在新线程中，相当于new Thread()，每次执行都会在新线程中
     * Schedulers.computation 一些需要CPU计算的操作，比如图形，视频等
     * AndroidSchedulers.mainThread 指定运行在Android主线程中
     *
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

    //------------------------实列接口----------------------------

    /**
     *  自定配置线程调度
     * @param start
     * @param count
     */
    @Override
    public void getMovies(int start, int count) {
        Observable<BaseInfo> observable2= RetrofitClientManager.getInstance().apiService.getMovies(start,count);
        Subscription subscribe = observable2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<BaseInfo>(mContext,dialog) {
                    @Override
                    protected void _onNext(BaseInfo baseInfo) {

                    }

                    @Override
                    protected void _onError(String message) {

                    }
                });

    }

    /**
     * 通用线程调度 schedulersTransformer() 方法
     * @param map
     */
    @Override
    public void login(Map<String, String> map) {
       apiService.login(map).compose(schedulersTransformer())
                 .subscribe(new ProgressSubscriber<BaseHttpResult<LoginBean>>(mContext) {
                    @Override
                    protected void _onNext(BaseHttpResult<LoginBean> loginBeanBaseHttpResult) {

                    }

                    @Override
                    protected void _onError(String message) {

                    }
                });

    }


//
//    @Override
//    public Subscription getMovies(int start, int count) {
//        Observable<BaseInfo> observable2= RetrofitClientManager.getInstance().apiService.getMovies(start,count);
//        Subscription subscribe = observable2.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<BaseInfo>() {//如果用mvc模式一定要重写Subscriber避免代码在activity过沉
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.i("test", "MovieModelImpl-------->>onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        if (mListener != null) {
////                            mListener.onFailure();//回调
////                            LogUtil.i("test", "MovieModelImpl-------->>onFailure"+e.getMessage());
////                        }
//                        LogUtil.i("test", "MovieModelImpl-------->>onFailure"+e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(BaseInfo movieBean) {
////                        if (mListener != null) {
////                            mListener.onSuccess(movieBean);//回调
////                            LogUtil.i("test", "MovieModelImpl-------->>onSuccess");
////                        }
//                        LogUtil.i("test", "MovieModelImpl-------->>onSuccess="+movieBean);
//                    }
//                });
//        return subscribe;
//    }

}
