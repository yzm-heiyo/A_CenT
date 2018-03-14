package com.centanet.hk.aplus.Utils;

import android.util.Log;

/**
 * Created by yangzm4 on 2018/1/18.
 * log工具类
 * 方便定位和过滤
 */
public class L {

    /**
     * tag
     */
    private static final String TAG="Centaline_A+";
    /**
     * debug log 开关
     */
    public static boolean isDebug=true;
    /**
     * release log 开关
     */
    public static boolean isRelease =true;

    private L(){
        throw new UnsupportedOperationException("Cannot be instance");
    }

    /**
     * 该方法设计为打印一些不是很重要的log或者刷屏log，比如查看步骤执行
     * @param clazz 类名
     * @param msg 信息
     */
    public static void d(String clazz,String msg){
        if(isDebug){
            Log.d(TAG,"debug:["+clazz+"]"+msg);
        }
    }

    /**
     * 该方法设计为打印一下些关键log，对软件的运行起决定作用，比如数据接受的正确与否
     * @param clazz 类名
     * @param msg  信息
     */
    public static void r(String clazz,String msg){
        if(isRelease){
            Log.d(TAG,"release:["+clazz+"]"+msg);
        }
    }



}
