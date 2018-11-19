package com.danqiu.myapplication.accessibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danqiu.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/19.
 */

public class AccessiblityMian extends AppCompatActivity {
    @BindView(R.id.bt_openset)
    Button btOpenset;
    @BindView(R.id.bt_next)
    Button btNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_openset, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_openset:
                OpenAccessibilitySettingHelper.jumpToSettingPage(this);
                break;
            case R.id.bt_next:
                startActivity(new Intent(this, AccessibilityClickAct.class));
                break;
        }
    }
}
