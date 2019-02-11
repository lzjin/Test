package com.danqiu.myapplication.mp3;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.Mp3Bean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/10.
 */

public class Mp3Activity extends AppCompatActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt_stop)
    Button btStop;
    @BindView(R.id.bt_naxt)
    Button btNaxt;

    public ArrayList<Mp3Bean> arrayList;
    private ServiceConnection conn;
    private  Mp3Service.MyBinder binder;
    private  Intent intent;

    String url2="http://ibooker.cc/ibooker/file_packet/musics/2345.mp3";
    MP3MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
        ButterKnife.bind(this);

        arrayList=new ArrayList<>();
        arrayList.add(new Mp3Bean("http://img-1253650823.cosgz.myqcloud.com/listeningTest/practice/1.mp3"));
        arrayList.add(new Mp3Bean("http://img-1253650823.cosgz.myqcloud.com/listeningTest/practice/2.mp3"));
        arrayList.add(new Mp3Bean("http://img-1253650823.cosgz.myqcloud.com/listeningTest/practice/3.mp3"));

        conn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("test", "onServiceConnected"+"连接服务");
                binder= (Mp3Service.MyBinder) service;
                binder.init(arrayList,tvTime,seekBar);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        intent=new Intent(this,Mp3Service.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);//如果服务不再，自动开启，再就绑定


    }

    @OnClick({R.id.bt_start, R.id.bt_stop, R.id.bt_naxt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                binder.startMusic(0);

                break;
            case R.id.bt_stop:
                binder.pause();
                break;
            case R.id.bt_naxt:
                binder.next(1);
                //seekBar.setSecondaryProgress(50);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
