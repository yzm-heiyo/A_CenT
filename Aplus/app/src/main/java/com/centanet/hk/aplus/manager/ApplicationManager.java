package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.entity.params.Parameter;
import com.centanet.hk.aplus.entity.params.SystemParam;

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

    public static void setLabelSystemParam(SystemParam labelSystemParam){
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setLabelSystenParam(labelSystemParam);
    }

    public static Parameter getParamter() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getParameter();
    }

    public static SystemParam getLabelSystenParam(){
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getLabelSystenParam();
    }


    public static SystemParam getDirectionSystemParam(){
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getDirectionSystemParam();
    }

    public static SystemParam getIntervalSystemParam(){
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getIntervalSystemParam();
    }

    public static void setDirectionSystemParam(SystemParam directionSystemParam){
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setDirectionSystemParam(directionSystemParam);
    }

    public static void setIntervalSystemParam(SystemParam intervalSystemParam){
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setIntervalSystemParam(intervalSystemParam);
    }




}
