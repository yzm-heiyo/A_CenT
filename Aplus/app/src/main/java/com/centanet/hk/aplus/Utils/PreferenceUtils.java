package com.centanet.hk.aplus.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.centanet.hk.aplus.MyApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yangzm4 on 2018/2/5.
 */

public class PreferenceUtils {

    private Context context;
    private String SharedID = "Centa_Params";
    private int Mode = Activity.MODE_PRIVATE;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shareEditor;
    private static PreferenceUtils preferenceUtils = null;

    @Retention(RetentionPolicy.RUNTIME)
    @interface ContextLifeCycle{
        String value();
    }
    @Inject @Singleton
    protected PreferenceUtils(@ContextLifeCycle("App") Context context) {
        sharedPreferences = context.getSharedPreferences(SharedID, Mode);
        shareEditor = sharedPreferences.edit();
    }

    public static PreferenceUtils getInstance(Context context){
        if (preferenceUtils == null) {
            synchronized (PreferenceUtils.class) {
                if (preferenceUtils == null) {
                    preferenceUtils = new PreferenceUtils(MyApplication.getContext());
                }
            }
        }
        return preferenceUtils;
    }

    public static void addParams(String key, String value){
        PreferenceUtils.getInstance(null).shareEditor.putString(key, value).commit();
    }

    public static String getValue(String key){
        return PreferenceUtils.getInstance(null).sharedPreferences.getString(key,"");
    }
}
