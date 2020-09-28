package com.danqiu.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * sp工具类
 */
public class SPUtil {

    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("config", context.MODE_PRIVATE);
    }

    /**
     * 写入
     */
    public static void put(Context context, String key, Object value) {
        Editor edit = getSP(context).edit();
        if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        }
        edit.apply();
    }

    /**
     * 获取int值
     *
     * @return 默认-1
     */
    public static int getInt(Context context, String key) {
        return getSP(context).getInt(key, -1);
    }

    /**
     * 获取String值
     *
     * @return 默认""
     */
    public static String getString(Context context, String key) {
        return getSP(context).getString(key, "");
    }

    /**
     * 获取Boolean值
     *
     * @return 默认false
     */
    public static boolean getBoolean(Context context, String key) {
        return getSP(context).getBoolean(key, false);
    }
    /**
     * 获取Long值
     *
     * @return 默认 -1L
     */
    public static long getLong(Context context, String key) {
        return getSP(context).getLong(key, -1L);
    }

    /**
     * 删除
     */
    public static boolean delete(Context context, String key) {
        return getSP(context).edit().remove(key).commit();
    }

    /**
     * 获取所有sp键值对
     */
    public static String getAll(Context context) {
        Map<String, ?> all = getSP(context).getAll();
        return all.toString();
    }

    /**
     * 是否存在某个键值
     */
    public static boolean contains(Context context, String key) {
        return getSP(context).contains(key);
    }

    /**
     * 清空sp，除了应用versionCode,update_notice_time,notice_times,suggest_update_version
     */
    public static void clearAll(Context context) {
        getSP(context).edit().remove("token").apply();
        getSP(context).edit().remove("phone").apply();
        getSP(context).edit().remove("password").apply();
        getSP(context).edit().remove("refreshToken").apply();
    }

}
