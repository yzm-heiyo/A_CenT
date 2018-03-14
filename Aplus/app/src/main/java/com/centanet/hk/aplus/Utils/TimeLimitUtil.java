package com.centanet.hk.aplus.Utils;

/**
 * Created by yangzm4 on 2018/1/29.
 */

public class TimeLimitUtil {

    private static long lastClickTime=0;

    public static boolean isAchieveLimitTime(int limitTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < limitTime) {       //limitTime毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return false;
        }
        lastClickTime = time;
        return true;
    }
}
