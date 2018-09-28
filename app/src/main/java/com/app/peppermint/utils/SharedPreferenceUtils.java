package com.app.peppermint.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.app.peppermint.BaseApplication;

public class SharedPreferenceUtils {

    public static SharedPreferences getPreferences(String prefName) {
        SharedPreferences pref = BaseApplication.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref;
    }

    public static void saveString(String prefName, String key, String content){
        SharedPreferences.Editor editor = getPreferences(prefName).edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static void saveString(SharedPreferences pref, String key, String content){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static String getString(SharedPreferences pref, String key){
        return pref.getString(key, null);
    }


    public static void saveBoolean(String prefName, String key, boolean enable){
        SharedPreferences.Editor editor = getPreferences(prefName).edit();
        editor.putBoolean(key, enable);
        editor.apply();
    }

    public static Boolean getBoolean(String prefFanme, String key){
        return getPreferences(prefFanme).getBoolean(key, false);
    }

}
