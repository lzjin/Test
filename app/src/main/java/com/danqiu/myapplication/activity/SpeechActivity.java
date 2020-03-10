package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.danqiu.myapplication.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzj on 2020/3/10
 * Describe ：注释
 */
public class SpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    private TextToSpeech tts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        ButterKnife.bind(this);
        initTTS();
    }

    private void initTTS() {
        tts = new TextToSpeech(this,this);
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                start("今天温度12");
                break;
            case R.id.bt2:
                start("今晚抄底");
                break;
            case R.id.bt3:
                stopTTS();
                break;
        }
    }
    private void start(String text){
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (tts != null) {
            //TextToSpeech.QUEUE_ADD会将加入队列的待播报文字按顺序播放 TextToSpeech.QUEUE_FLUSH会替换原有文字
           tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            // tts.addSpeech("",new File("aa.mp3"));
        }
    }

    public void stopTTS() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

    /**
     * 用来初始化TextToSpeech引擎
     *
     * @param status SUCCESS或ERROR这2个值
     */
    @Override
    public void onInit(int status) {
        //初始化成功,
        if (status == TextToSpeech.SUCCESS) {
            //设置播放语言
            int languageResult = tts.setLanguage(Locale.CHINESE);
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            // tts.setPitch(1.0f);//使用讯飞语音引擎这里不管
            // 设置语速
            //tts.setSpeechRate(0.5f);//使用讯飞语音引擎这里不管
            if (languageResult == TextToSpeech.LANG_MISSING_DATA || languageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(SpeechActivity.this, "语音不支持", Toast.LENGTH_SHORT).show();
            }else if (languageResult == TextToSpeech.LANG_AVAILABLE) {
                Toast.makeText(SpeechActivity.this, "初始化启动成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SpeechActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
        }
    }
}