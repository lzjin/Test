package com.danqiu.myapplication.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.videoview.IjkPlayerVedioView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/20.
 */

public class VedioPlayerActivity extends BaseActivity {


    @BindView(R.id.player_vedio_view)
    IjkPlayerVedioView playerVedioView;

    String url="http://img-1253650823.cosgz.myqcloud.com/5ece9bb3-43db-4074-b143-916507b64475.mp4";

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vedio_player);
        ButterKnife.bind(this);

        playerVedioView.setVideoUrl(url);
        playerVedioView.playVideo();
        playerVedioView.setTitle("视频标题");

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//始终横屏
            playerVedioView.horizontalScreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//始终竖屏
            playerVedioView.verticalScreen();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerVedioView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerVedioView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerVedioView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerVedioView.onPause();
    }
}
