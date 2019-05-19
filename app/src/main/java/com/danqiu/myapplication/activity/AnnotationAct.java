package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.danqiu.myapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnnotationAct extends BaseActivity {
    @BindView(R.id.bt_thread)
    Button btThread;
    @BindView(R.id.tv_thread)
    TextView tvThread;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_annotation);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.bt_thread, R.id.tv_thread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_thread:
                httpData();

                break;
            case R.id.tv_thread:
                break;
        }
    }

    private void httpData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    setMainData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @MainThread
    private  void setMainData(){

        tvThread.setText("你好");
    }

}
