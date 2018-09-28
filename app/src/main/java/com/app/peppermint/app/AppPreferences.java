/*
 * All rights Reserved, Designed By 农金圈 2017年3月30日 下午9:17:12
 */
package com.app.peppermint.app;

import android.content.SharedPreferences;
import com.app.peppermint.utils.SharedPreferenceUtils;

/**
 * Created by Administrator on 2015/11/25.
 */
public class AppPreferences {

    private static SharedPreferences sharedPreferences;

    public static void initPreferences(String prefName) {
        sharedPreferences = SharedPreferenceUtils.getPreferences(prefName);
    }

    public static void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public static void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static int getInt(String key) {
        return sharedPreferences.getInt(key, -10000);
    }

}
