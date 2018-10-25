package com.danqiu.myapplication.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/9/28.
 * 带进度 下载  拦截器
 */


public class DownloadProgressInterceptor implements Interceptor {
    private JsDownloadListener listener;

    public DownloadProgressInterceptor(JsDownloadListener listener){
        this.listener = listener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //断点续传
//        Request request = chain.request().newBuilder().addHeader("Range" , "bytes=" + startpos + "-").build();
//        Response response = chain.proceed(request);
//        return response.newBuilder()
//                .body(new JsResponseBody(originalResponse.body(), listener))
//                .build();

        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new JsResponseBody(response.body(), listener))
                .build();


    }
}
