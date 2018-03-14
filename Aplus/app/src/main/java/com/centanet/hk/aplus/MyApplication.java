package com.centanet.hk.aplus;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MyApplication extends LitePalApplication {

    private static List<Activity> activities;

    private static boolean isRelase;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        isRelase = false;
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static boolean getRelase() {
        return isRelase;
    }


    public static boolean addActivity(Activity activity) {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        return activities.add(activity);
    }

    public static boolean removeActivity(Activity activity) {
        if (activities != null) {
            return activities.remove(activity);
        }
        return false;
    }

    public static boolean removeAllActiies() {

        if (activities == null) {
            return true;
        }

        if (activities.size() > 0) {
            for (int i = 0; i < activities.size(); i++) {
                activities.get(i).finish();
            }

            activities.clear();
        }

        return activities.size() == 0;
    }


    public static int getActiviesSize() {

        return activities.size();
    }

}
