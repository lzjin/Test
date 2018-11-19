package com.danqiu.myapplication.accessibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 辅助功能权限打开辅助activity，用于启动辅助功能设置页面
 */

public class OpenSetHelperActivity extends AppCompatActivity{

    private static final String ACTION = "action";
    private static final String ACTION_FINISH_SELF = "action_finis_self";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        if (intent != null && intent.getExtras() != null) {
//            String action = intent.getStringExtra(ACTION);
//            if (ACTION_FINISH_SELF.equals(action)) {
//                finishCurrentActivity();
//                return;
//            }
//        }
//        mTimeoutCounter = 0;
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra(ACTION);
            if (ACTION_FINISH_SELF.equals(action)) {
               // finishCurrentActivity();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jumpActivities();

        if (AccessibilityUtil.isAccessibilitySettingsOn(OpenSetHelperActivity.this)) {
            try {
                OpenSetHelperActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OpenSetHelperActivity.this, "辅助功能开启成功", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent();
                intent.putExtra(ACTION, ACTION_FINISH_SELF);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(OpenSetHelperActivity.this, OpenSetHelperActivity.this.getClass());
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void jumpActivities() {
        try {
            Intent intent = AccessibilityUtil.getAccessibilitySettingPageIntent(this);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





}
