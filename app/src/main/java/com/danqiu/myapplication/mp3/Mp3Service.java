package com.danqiu.myapplication.mp3;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.danqiu.myapplication.bean.Mp3Bean;
import com.danqiu.myapplication.utils.MLog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2019/1/10.
 *   mp3播放服务
 */

public class Mp3Service extends Service implements MediaPlayer.OnCompletionListener{
    public MediaPlayer mediaPlayer;
    private ArrayList<Mp3Bean> arrayLists;
    private int newstart=-1;//当前播放
    private Timer mTimer;

    private TimerTask mTimerTask;
    private SeekBar mSeekBar;
    private TextView mTvTime;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public  class MyBinder extends Binder {
        public  void init(ArrayList<Mp3Bean> arrayList, TextView tvTime,SeekBar seekBar){
            Mp3Service.this.init(arrayList);
            Mp3Service.this.mSeekBar=seekBar;
            Mp3Service.this.mTvTime=tvTime;
        }
        public  void startMusic(int postion){
            Mp3Service.this.startMusic(postion);//内部类调外部类
        }
        public  void keepStart(){
            Mp3Service.this.keepStart();
        }
        public void pause(){
            Mp3Service.this.pauseMusic();
        }
        public  void next(int postion){
            Mp3Service.this.nextMusic(postion);
        }
    }

    //------------------
    public void init(ArrayList<Mp3Bean> arrayList){
        this.arrayLists=arrayList;
    }

    //开始条目播放---------------------------------------------------------
    public  void startMusic(int postion){
        newstart=postion;

        Log.i("test", "条目播放" +  postion);
        String uri=arrayLists.get(newstart).getUrl();
        try {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //时间
//        mSeekBar.setMax(mediaPlayer.getDuration());
//        Date d = new Date(mediaPlayer.getDuration() - TimeZone.getDefault().getRawOffset());
//        mTvTime.setText("00:00:00/" + sdf.format(d));

        startTimer();
    }
    //继续播放-----------------------------
    public  void keepStart(){

        String uri=arrayLists.get( newstart-1).getUrl();
        if(newstart==1) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    //点击停止---
    public  void pauseMusic(){
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    //点击下一曲---
    public  void nextMusic(int postion){
        newstart=postion;
        Log.i("test", "播放下一曲-" + postion);
        String uri=arrayLists.get(postion).getUrl();
        try {
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();//播放时下一曲
                mediaPlayer.reset();
            }
            else {
                mediaPlayer.reset();//没有播放时下一曲
            }
            mediaPlayer.setDataSource(uri);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        startTimer();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getStringExtra("class")) {
            case "play":
                //   createNotification() ;
                break;
            case "play2":

                break;
        }

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i("test", "onCreate");
        super.onCreate();
        mediaPlayer  = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("test", "onBind" + "绑定");
        return new MyBinder();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("test", "onUnbind"+"解除绑定");
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        Log.i("test", "service-onDestroy");
        super.onDestroy();
//        if(mediaPlayer != null){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
    }

    /**
     *  播放完成  自动下一首
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(newstart<arrayLists.size()){

            newstart=newstart+1;
            String uri=arrayLists.get( newstart-1).getUrl();
            mediaPlayer.reset();
            try {

                mediaPlayer.setDataSource(uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            startMusic(0);
        }

    }


    private void startTimer() {
        if (mTimer == null && mTimerTask == null) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                   MLog.i("test","Current = " + mediaPlayer.getCurrentPosition());
                    MLog.i("test","Duration= " + mediaPlayer.getDuration());
                   // MLog.d("buffer = " + mediaPlayer.getVideoView().getBufferPercentage());
//                    MLog.d("speed = "+showNetSpeed());
//                    MLog.d("trueSpeed = "+player.getVideoView().getIjkMediaPlayer().getTcpSpeed()/1024);
                   // mSeekBar.setSecondaryProgress((int) mediaPlayer.getCurrentPosition()  );
                }
            };
            mTimer.schedule(mTimerTask, 0, 1000);
        }
    }


}
