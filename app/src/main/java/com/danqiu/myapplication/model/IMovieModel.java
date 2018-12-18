package com.danqiu.myapplication.model;

import java.util.Map;

/**
 * Created by Administrator on 2018/9/26.
 *
 */

public interface IMovieModel{

    void getMovies(int start, int count);

    void login(Map<String, String> map);

}
