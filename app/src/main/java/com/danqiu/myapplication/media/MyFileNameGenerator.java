package com.danqiu.myapplication.media;

import android.net.Uri;

import com.danikula.videocache.file.FileNameGenerator;

/**
 * Created by dhh on 2017/5/5.
 */

public class MyFileNameGenerator implements FileNameGenerator {
    @Override
    public String generate(String url) {
        Uri uri = Uri.parse(url);
        String videoId = uri.getQueryParameter("videoId");

        String[] str = url.split("/");
        if(str[4] != null){
            videoId = str[4];
        }
        return videoId + ".m3u8";
    }
}
