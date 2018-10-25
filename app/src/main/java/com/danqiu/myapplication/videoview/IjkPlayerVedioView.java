package com.danqiu.myapplication.videoview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.media.ChangeOrientationHandler;
import com.danqiu.myapplication.media.IjkVideoView;
import com.danqiu.myapplication.media.OrientationSensorListener;
import com.danqiu.myapplication.media.PlayerManager;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.SystemUtil;
import com.danqiu.myapplication.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by dhh on 2017/5/18.
 */

public class IjkPlayerVedioView extends RelativeLayout implements PlayerManager.PlayerStateListener {

    @BindView(R.id.video_rl)
    RelativeLayout rl;
    @BindView(R.id.video_controll)
    RelativeLayout controll;
    @BindView(R.id.play_or_pause)
    ImageView play_pause;
    @BindView(R.id.bg_controll_view)
    View bgControll;
    @BindView(R.id.video_loading)
    ProgressBar loading;
    @BindView(R.id.video_fullscreen)
    ImageView full;
    @BindView(R.id.video_progress)
    SeekBar seekBar;
    @BindView(R.id.mySeekBar)
    SeekBar voiceSeekBar;
    @BindView(R.id.video_voice)
    ImageView voice;
    @BindView(R.id.video_click)
    View showHideControllView;
    @BindView(R.id.video_title_view)
    View bgTitle;
    @BindView(R.id.video_title)
    TextView title;
    @BindView(R.id.video_play_time)
    TextView playerTime;
    @BindView(R.id.video_cover)
    ImageView cover;
    @BindView(R.id.net_speed)
    TextView buffer;
    @BindView(R.id.play_next)
    ImageView playNext;
    @BindView(R.id.curriumlum_detail_play)
    TextView play;
    @BindView(R.id.video_view)
    IjkVideoView videoView;

    private Activity activity;

    private String videoUrl;

    private PlayerManager player;

    private boolean isFull = false;//是否全屏

    private Timer mTimer;

    private TimerTask mTimerTask;

    private AudioManager mAudioManager;

    private GestureDetectorCompat mGestureDetector;

    private boolean isScroll = false;

    private Handler handler;
    private OrientationSensorListener listener;//重力旋转监听
    private SensorManager sm;
    private Sensor sensor;

    private boolean flag = true;

    private boolean isPortrait = true;//true只能水平180度旋转，false能够90度旋转

    public boolean isPortrait() {
        return isPortrait;
    }

    public void setPortrait(boolean portrait) {
        isPortrait = portrait;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        player.setUrl(videoUrl);
    }

    public IjkVideoView getVideoView() {
        return videoView;
    }

    public PlayerManager getPlayer() {
        return player;
    }

    public ProgressBar getLoading() {
        return loading;
    }

    public TextView getBuffer() {
        return buffer;
    }

    public boolean isFull() {
        return isFull;
    }

    public IjkPlayerVedioView(Context context) {
        this(context, null);
    }

    public IjkPlayerVedioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IjkPlayerVedioView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IjkPlayerVedioView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context, R.layout.video_play_layout, this);
        ButterKnife.bind(this, this);
        activity = (Activity) context;
        gravityReversal();//重力翻转
        if(isPortrait)full.setVisibility(View.INVISIBLE);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetectorCompat(context, new MyGestureListener());
        initPlayer();
        voiceControll();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Date d1 = new Date(seekBar.getMax());
                Date d2 = new Date(seekBar.getProgress());
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                playerTime.setText(sdf.format(d2) + "/" + sdf.format(d1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopTimer();
                timer.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startTimer();
                int progress = seekBar.getProgress();
                if (progress < 1000) {
                    player.onDestroy();
                    initPlayer();
                    player.setUrl(videoUrl);
                    player.start();
                    seekBar.setProgress(0);
                } else if (progress > player.getDuration() - 1000) {
                    player.seekTo(player.getDuration());
                } else {
                    player.seekTo(seekBar.getProgress());
                }
                timer.start();
            }
        });
    }

    private void gravityReversal() {
        handler = new ChangeOrientationHandler(activity,isPortrait);
        sm = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener(handler);
//        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);//开启重力翻转
    }

    private void initPlayer() {
        player = new PlayerManager(activity, videoView);
        player.setFullScreenOnly(true);
        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        player.playInFullScreen(true);
        player.setPlayerStateListener(this);
        if (videoView == null) videoView = player.getVideoView();
        horizontalScreen();
        seekBar.setProgress(0);

        showHideControllView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean detectedUp = event.getAction() == MotionEvent.ACTION_UP;
                if (!mGestureDetector.onTouchEvent(event) && detectedUp) {
                    flag = true;
                    MLog.d("ACTION UP");
                    startTimer();
                    if (isScroll) {
                        timer.start();
                        isScroll = false;
                        player.seekTo(seekBar.getProgress());
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onComplete(IMediaPlayer mp) {
        play_pause.setImageResource(R.mipmap.pay_3);
        player.onDestroy();
        initPlayer();
        player.setUrl(videoUrl);
        stopTimer();
        MLog.d("onComplete");
    }

    @Override
    public void onError(IMediaPlayer mp, int what, int extra) {
        MLog.d("onError = " + what + "    " + extra);
        loading.setVisibility(View.GONE);
        buffer.setVisibility(View.GONE);
        ToastUtil.showShort(activity, "视频播放出错了");
    }

    @Override
    public void onLoading(IMediaPlayer mp, int what, int extra) {
        loading.setVisibility(View.VISIBLE);
        buffer.setVisibility(View.VISIBLE);
        MLog.d("onLoading");
    }

    @Override
    public void onPlay(IMediaPlayer mp, int what, int extra) {
        MLog.d("onPlay");
        loading.setVisibility(View.GONE);
        buffer.setVisibility(View.GONE);
        seekBar.setMax(player.getDuration());
        Date d = new Date(player.getDuration());
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        playerTime.setText("00:00/" + sdf.format(d));
        startTimer();
        timer.start();
    }

    @Override
    public void onPrepare(IMediaPlayer mp) {
        MLog.d("onPrepare");
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    private class MyGestureListener implements android.view.GestureDetector.OnGestureListener {

        int progress;

        @Override
        public boolean onDown(MotionEvent e) {
            progress = seekBar.getProgress();
            MLog.d("onDown");
            MLog.d("max = " + seekBar.getMax());
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            MLog.d("onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            MLog.d("onSingleTapUp");
            if (controll.getVisibility() == View.GONE && bgControll.getVisibility() == View.GONE && play.getVisibility() == View.GONE) {
                controll.setVisibility(View.VISIBLE);
                bgControll.setVisibility(View.VISIBLE);
                timer.start();
//                if (isFull) {
//                    title.setVisibility(View.VISIBLE);
//                    bgTitle.setVisibility(View.VISIBLE);
//                }
            } else {
                controll.setVisibility(View.GONE);
                bgControll.setVisibility(View.GONE);
                voiceSeekBar.setVisibility(View.GONE);
//                if (isFull) {
//                    title.setVisibility(View.GONE);
//                    bgTitle.setVisibility(View.GONE);
//                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            MLog.d("e1X = " + e1.getX());
            MLog.d("e2X = " + e2.getX());
            isScroll = true;
            float x = e2.getX() - e1.getX();
            seekBar.setProgress((int) ((float) progress + (x / (float) SystemUtil.getScreenWidth(activity)) * (float) seekBar.getMax()));
            MLog.d("disX = " + (int) ((float) progress + (x / (float) SystemUtil.getScreenWidth(activity)) * (float) seekBar.getMax()));
            MLog.d("chaX = " + x);
            if (flag) {//设置布尔变量保证此段代码只被执行一次
                if (controll.getVisibility() == View.GONE && bgControll.getVisibility() == View.GONE && play.getVisibility() == View.GONE) {
                    controll.setVisibility(View.VISIBLE);
                    bgControll.setVisibility(View.VISIBLE);
                    timer.start();
//                    if (isFull) {
//                        title.setVisibility(View.VISIBLE);
//                        bgTitle.setVisibility(View.VISIBLE);
//                    }
                }
                stopTimer();
                timer.cancel();
                flag = false;
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            MLog.d("onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            MLog.d("onFling");
            return false;
        }
    }

    //当手机为竖屏时候的操作
    public void verticalScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
            }
        }, 3000);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rl.getLayoutParams();
        params.height = SystemUtil.getScreenWidth(activity) / 16 * 9;
        rl.setLayoutParams(params);
//        scrollView.setOnTouchListener(new scrollTouchListner(false));
//        bgTitle.setVisibility(View.GONE);
//        title.setVisibility(View.GONE);
        full.setImageResource(R.mipmap.tu1);
        isFull = false;
//        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
    }

    //当手机以为横屏时候的操作
    public void horizontalScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
            }
        }, 3000);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rl.getLayoutParams();
        params.height = SystemUtil.getScreenHeight(activity);
        rl.setLayoutParams(params);
//        bgTitle.setVisibility(View.VISIBLE);
//        title.setVisibility(View.VISIBLE);
        full.setImageResource(R.mipmap.tu2);
        isFull = true;
//        player.setScaleType(PlayerManager.SCALETYPE_FITXY);
    }

    public void setTitle(String text){
        title.setText(text);
    }

    public void setCover(String url){
       // ImageLoader.displayImageView(cover,url);
    }

    public void unregitstSm(){
        sm.unregisterListener(listener);
    }

    private void voiceControll() {
        voiceSeekBar.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        voiceSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        voiceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                timer.cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timer.start();
            }
        });
    }

    public void onResume() {
        player.onResume();
        play_pause.setImageResource(R.mipmap.pay_2);
        registerVolumeChangeReceiver();
        registConnectChangedReceiver();
    }

    public void onPause() {
        player.onPause();
        stopTimer();
    }

    public void onDestroy() {
        player.onDestroy();
        stopTimer();
        unregisterVolumeChangeReceiver();
        activity.unregisterReceiver(networkConnectChangedReceiver);
        unregitstSm();//关闭重力翻转
    }

    public void onStop() {
        player.stop();
        stopTimer();
    }

    private void registConnectChangedReceiver() {
        IntentFilter filter = new IntentFilter();
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        activity.registerReceiver(networkConnectChangedReceiver, filter);
    }

    private BroadcastReceiver networkConnectChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            switch (NetWorkUtils.getAPNType(context)) {
//                case 0:
//                    pauseVideo();
//                    MLog.d("onReceive0");
//                    break;
//                case 1:
//                    if (videoView.getVisibility() == View.VISIBLE)
//                        playVideo();
//                    MLog.d("onReceive1");
//                    break;
//                case 2:
//                    if (SavaData.getWifiState(activity) != 1) {
//                        if (videoView.getVisibility() == View.VISIBLE)
//                            playVideo();
//                    } else {
//                        if (player.isPlaying())ToastUtil.showShort(activity, "非wifi环境已暂停播放");
//                        pauseVideo();
//                    }
//                    MLog.d("onReceive2");
//                    break;
//                case 3:
//                    if (SavaData.getWifiState(activity) != 1) {
//                        if (videoView.getVisibility() == View.VISIBLE)
//                            playVideo();
//                    } else {
//                        if (player.isPlaying())ToastUtil.showShort(activity, "非wifi环境已暂停播放");
//                        pauseVideo();
//                    }
//                    MLog.d("onReceive3");
//                    break;
//                case 4:
//                    if (SavaData.getWifiState(activity) != 1) {
//                        if (videoView.getVisibility() == View.VISIBLE)
//                            playVideo();
//                    } else {
//                        if (player.isPlaying())ToastUtil.showShort(activity, "非wifi环境已暂停播放");
//                        pauseVideo();
//                    }
//                    MLog.d("onReceive4");
//                    break;
//            }
        }
    };

    private SettingsContentObserver mSettingsContentObserver;

    private void registerVolumeChangeReceiver() {
        mSettingsContentObserver = new SettingsContentObserver(activity, new Handler());
        activity.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver);
    }

    private void unregisterVolumeChangeReceiver() {
        activity.getContentResolver().unregisterContentObserver(mSettingsContentObserver);
    }

    //监听音量变化
    private class SettingsContentObserver extends ContentObserver {
        Context context;

        public SettingsContentObserver(Context c, Handler handler) {
            super(handler);
            context = c;
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            voiceSeekBar.setProgress(currentVolume);
            //TODO
        }
    }


    private void startTimer() {
        if (mTimer == null && mTimerTask == null) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    seekBar.setProgress(player.getCurrentPosition());
                    seekBar.setSecondaryProgress((int) (player.getVideoView().getBufferPercentage() / 100.0 * player.getDuration()));
                }
            };
            mTimer.schedule(mTimerTask, 0, 1000);
        }
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private CountDownTimer timer = new CountDownTimer(8000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            controll.setVisibility(View.GONE);
            bgControll.setVisibility(View.GONE);
            voiceSeekBar.setVisibility(View.GONE);
//            if (isFull) {
//                title.setVisibility(View.GONE);
//                bgTitle.setVisibility(View.GONE);
//            }
        }
    };

    public void firstPlay() {
        play.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
        controll.setVisibility(View.VISIBLE);
        bgControll.setVisibility(View.VISIBLE);
        showHideControllView.setVisibility(View.VISIBLE);
//        if (SavaData.getWifiState(activity) == 1) {
//            if (NetWorkUtils.getAPNType(activity) != 1) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                builder.setTitle("当前处于非wifi网络，是否继续播放")
//                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                play_pause.setImageResource(R.mipmap.pay_3);
//                            }
//                        })
//                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                loading.setVisibility(View.VISIBLE);
//                                buffer.setVisibility(View.VISIBLE);
//                                player.play();
//                            }
//                        }).create().show();
//            } else {
//                loading.setVisibility(View.VISIBLE);
//                buffer.setVisibility(View.VISIBLE);
//                player.play();
//            }
//        } else {
            loading.setVisibility(View.VISIBLE);
            buffer.setVisibility(View.VISIBLE);
            player.play();
//        }
    }

    public void playVideo() {
        if (!player.isPlaying()) {
            play.setVisibility(View.GONE);
            startTimer();
            player.play();
            play_pause.setImageResource(R.mipmap.pay_2);
        }
    }

    private void pauseVideo() {
        if (player.isPlaying()) {
            player.pause();
            stopTimer();
            play_pause.setImageResource(R.mipmap.pay_3);
        }
    }

    @OnClick({R.id.curriumlum_detail_play,R.id.play_or_pause,R.id.video_fullscreen,R.id.video_voice,
    R.id.video_click,R.id.play_next})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.curriumlum_detail_play:
                if (videoView.getVisibility() == View.GONE) {
                    firstPlay();
                } else {
                    playVideo();
                }
                break;
            case R.id.play_or_pause:
                if (player.isPlaying()) {
                    pauseVideo();
                    play.setVisibility(View.VISIBLE);
                } else {
                    playVideo();
                    play.setVisibility(View.GONE);
                }
                break;
            case R.id.video_fullscreen:
                sm.unregisterListener(listener);
                if (isFull) player.playInFullScreen(false);
                else player.playInFullScreen(true);
                break;
            case R.id.video_voice:
                if (voiceSeekBar.getVisibility() == View.GONE) {
                    voiceSeekBar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
                    voiceSeekBar.setVisibility(View.VISIBLE);
                } else voiceSeekBar.setVisibility(View.GONE);
                break;
            case R.id.video_click:

                break;
            case R.id.play_next:
//                if (currentPosition + 1 == adapter.getCount()) {
//                    ToastUtil.showShort(activity, "这已经是最后一个视频了");
//                } else {
//                    loading.setVisibility(View.VISIBLE);
//                    speed.setVisibility(View.VISIBLE);
//                    CurriculumListBean.DataBean bean = adapter.getItem(currentPosition + 1);
//                    title.setText(bean.getTitle());
//                    player.onDestroy();
//                    videoUrl = bean.getmVideoUrl();
//                    player.setUrl(videoUrl);
//                    playVideo();
//                    currentPosition++;
//                }
                break;
        }
    }

}
