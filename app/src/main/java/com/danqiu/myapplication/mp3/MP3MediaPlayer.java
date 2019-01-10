package com.danqiu.myapplication.mp3;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;

import java.io.IOException;

/**
 * Created by Administrator on 2019/1/10.
 */

public class MP3MediaPlayer  implements MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,MediaPlayer.OnSeekCompleteListener{
    private Context mContext;
    MediaPlayer mediaPlayer;
    boolean isPause=true;

    public void initMediaPlayer(Context context) {
        this.mContext = context;
        //this.mediaPlayer = new MediaPlayer();
         this.mediaPlayer = MediaPlayer.create(context, R.raw.aa);
        this.mediaPlayer.setOnErrorListener(this);
        this.mediaPlayer.setOnCompletionListener(this);
        this.mediaPlayer.setOnPreparedListener(this);
        this.mediaPlayer.setOnSeekCompleteListener(this);
    }



    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        MLog.i("test","-----------------onError---------");
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        MLog.i("test","-----------------onCompletion---------");
    }
    //准备Prepared完成监听
    @Override
    public void onPrepared(MediaPlayer mp) {
        MLog.i("test","-----------------onPrepared---------");
    }
    //进度调整完成SeekComplete监听，主要是配合seekTo(int)方法
    @Override
    public void onSeekComplete(MediaPlayer mp) {
        MLog.i("test","-----------------onSeekComplete---------");
    }
    private void restart(){
        if (mediaPlayer.isPlaying())
            mediaPlayer.reset();
    }

    public void startMusic(String url){

        try {
            // 方式二
            AssetFileDescriptor fd = mContext.getAssets().openFd("aa.mp3");

             mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(), fd.getLength());

            mediaPlayer.prepare();//初始化播放器MediaPlayer
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void stopMusic(){
        if (mediaPlayer.isPlaying()) {
            isPause = true;
            mediaPlayer.pause();
        }
    }
    // 下一首
    private void nextMusic(String url) {
        mediaPlayer.reset();
        initMediaPlayer(mContext);//初始化播放器 MediaPlayer
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();//初始化播放器MediaPlayer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void end(){
        // 释放mediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
