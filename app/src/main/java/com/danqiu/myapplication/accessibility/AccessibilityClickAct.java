package com.danqiu.myapplication.accessibility;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/19.
 */

public class AccessibilityClickAct extends AppCompatActivity {
    @BindView(R.id.normal_sample_checkbox)
    CheckBox normalSampleCheckbox;
    @BindView(R.id.normal_sample_radiobutton)
    RadioButton normalSampleRadiobutton;
    @BindView(R.id.normal_sample_togglebutton)
    ToggleButton normalSampleTogglebutton;
    @BindView(R.id.normal_sample_back)
    Button normalSampleBack;
    @BindView(R.id.activity_accessibility_main)
    LinearLayout activityAccessibilityMain;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility_sample);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.normal_sample_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                simulationClickByText();
               // simulationClickById();
            }
        }, 2000);
    }

    private void simulationClickByText() {
        boolean result = AccessibilityOperator.getInstance().clickByText("复选框开关");
        MLog.i("test",result ? "复选框模拟点击成功" : "复选框模拟点击失败");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = AccessibilityOperator.getInstance().clickByText("单选按钮");
                MLog.i("test",result ? "单选按钮模拟点击成功" : "单选按钮模拟点击失败");
            }
        }, 2000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = AccessibilityOperator.getInstance().clickByText("OFF");
                MLog.i("test",result ? "OnOff开关模拟点击成功" : "OnOff开关模拟点击失败");
            }
        }, 4000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = AccessibilityOperator.getInstance().clickByText("退出本页面");
                MLog.i("test",result ? "退出本页面模拟点击成功" : "退出本页面模拟点击失败");
            }
        }, 6000);
    }

    private void simulationClickById() {
        boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_checkbox");
        MLog.i("test",result ? "复选框模拟点击成功" : "复选框模拟点击失败");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_radiobutton");
                MLog.i("test",result ? "单选按钮模拟点击成功" : "单选按钮模拟点击失败");
            }
        }, 2000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_togglebutton");
                MLog.i("test",result ? "OnOff开关模拟点击成功" : "OnOff开关模拟点击失败");
            }
        }, 4000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                boolean result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_back");
//                AccessibilityLog.printLog(result ? "退出本页面模拟点击成功" : "退出本页面模拟点击失败");
                // 下面这个模拟点击系统返回键
                boolean result = AccessibilityOperator.getInstance().clickBackKey();
                MLog.i("test",result ? "返回键模拟点击成功" : "返回键模拟点击失败");
            }
        }, 6000);
    }
}
