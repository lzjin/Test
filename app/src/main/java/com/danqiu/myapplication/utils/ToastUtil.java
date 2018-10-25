package com.danqiu.myapplication.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Admin on 2017/2/9.
 *
 */

public class ToastUtil {

    private static Toast toast;

    public static void showShort(Context context, String text){
        if(toast == null) toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
        else toast.setText(text);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public static void showShort(Context context, int textRes){
        if(toast == null) toast = Toast.makeText(context,context.getResources().getString(textRes), Toast.LENGTH_SHORT);
        else toast.setText(context.getResources().getString(textRes));
        toast.show();
    }
    public static void showLong(Context context, String text){
        if(toast == null) toast = Toast.makeText(context,text, Toast.LENGTH_LONG);
        else toast.setText(text);
        toast.show();
    }
}
