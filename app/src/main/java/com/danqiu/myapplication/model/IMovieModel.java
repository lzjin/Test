package com.danqiu.myapplication.model;

import java.util.Map;

import rx.Subscription;

/**
 * Created by Administrator on 2018/9/26.
 *
 */

public interface IMovieModel{
    //Subscription getMovies(int start, int count, MovieModelImpl.OnMovieResultListener mListener);
    Subscription getMovies(int start, int count);

    void login(Map<String, String> map);

}
