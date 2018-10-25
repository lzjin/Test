package com.danqiu.myapplication.media;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ChangeOrientationHandler extends Handler {
    private Activity activity;

    private boolean isPORTRAIT;

    public ChangeOrientationHandler(Activity ac, boolean isPORTRAIT) {
        super();
        activity = ac;
        this.isPORTRAIT = isPORTRAIT;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == 888) {
            int orientation = msg.arg1;
            if (orientation > 80 && orientation < 100) {
                if(!isPORTRAIT)activity.setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//                MLog.d("横屏翻转: "+isPORTRAIT);
            } else if (orientation > 170 && orientation < 190) {
                activity.setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//                MLog.d("竖屏翻转: "+isPORTRAIT);
            } else if (orientation > 260 && orientation < 280) {
                if(!isPORTRAIT)activity.setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
//                MLog.d("横屏: "+isPORTRAIT);
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                activity.setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
//                MLog.d("竖屏: "+isPORTRAIT);
            }
        }
        super.handleMessage(msg);
    }
}
