package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.entity.params.Parameter;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public class ApplicationManager {

    public static MyApplication getApplication() {
        return (MyApplication) MyApplication.getContext().getApplicationContext();
    }

    public static void setOpenDataType(int type) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setOpenDataType(type);
    }

    public static int getOpenDataType() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getOpenDataType();
    }

    public static void setParamter(Parameter paramter) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setParameter(paramter);
    }

    public static Parameter getParamter() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getParameter();
    }

}
