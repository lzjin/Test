package com.danqiu.myapplication.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/3/22.
 */

public class IntentUtil {

    public static void IntenToActivity(Activity thisActivity, Class toActivity){
        Intent intent = new Intent(thisActivity,toActivity);
        thisActivity.startActivity(intent);
    }

    public static void IntenToActivityWithBundle(Activity thisActivity, Class toActivity, Bundle bundle){
        Intent intent = new Intent(thisActivity,toActivity);
        if(bundle != null) intent.putExtras(bundle);
        thisActivity.startActivity(intent);
    }

    public static void IntenToActivityResult(Activity thisActivity, Class toActivity,int requstCode){
        Intent intent = new Intent(thisActivity,toActivity);
        thisActivity.startActivityForResult(intent,requstCode);
    }

    public static void IntenToActivityResultWithBundle(Activity thisActivity, Class toActivity,int requstCode,Bundle bundle){
        Intent intent = new Intent(thisActivity,toActivity);
        intent.putExtras(bundle);
        thisActivity.startActivityForResult(intent,requstCode);
    }

    public static void IntentFinishActivityResult(Activity activity,Bundle bundle){
        Intent intent = new Intent();
        intent.putExtras(bundle);
        activity.setResult(activity.RESULT_OK,intent);
        activity.finish();
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(Activity activity,String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
