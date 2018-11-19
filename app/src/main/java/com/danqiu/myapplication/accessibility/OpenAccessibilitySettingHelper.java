package com.danqiu.myapplication.accessibility;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2018/11/16.
 */

public class OpenAccessibilitySettingHelper {
    private static final String ACTION = "action";
    private static final String ACTION_START_ACCESSIBILITY_SETTING = "action_start_accessibility_setting";

    public static void jumpToSettingPage(Context context) {
        try {
            Intent intent = new Intent(context,  OpenSetHelperActivity.class);
            intent.putExtra(ACTION, ACTION_START_ACCESSIBILITY_SETTING);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception ignore) {}
    }
}
