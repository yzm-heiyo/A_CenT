package com.centanet.hk.aplus;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOHeaderDescription;
import com.centanet.hk.aplus.entity.login.Permisstions;
import com.centanet.hk.aplus.entity.login.UserPermission;
import com.centanet.hk.aplus.entity.params.Parameter;
import com.centanet.hk.aplus.entity.params.SystemParam;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MyApplication extends LitePalApplication {

    private static List<Activity> activities;

    private static boolean isRelase;

    private String useClauseUrl;

    private int openDataType;

    private AHeaderDescription headerDescription;

    private SSOHeaderDescription ssoHeaderDescription;

    public static Context context;

    private Permisstions userPermission;

    private Parameter parameter;

    private SystemParam intervalSystemParam;

    private SystemParam directionSystemParam;

    private SystemParam labelSystenParam;

    @Override
    public void onCreate() {
        super.onCreate();
        isRelase = false;
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public void setUseClauseUrl(String useClauseUrl) {
        this.useClauseUrl = useClauseUrl;
    }

    public String getUseClauseUrl() {
        return useClauseUrl;
    }

    public void setSsoHeaderDescription(SSOHeaderDescription ssoHeaderDescription) {
        this.ssoHeaderDescription = ssoHeaderDescription;
    }

    public SystemParam getIntervalSystemParam() {
        return intervalSystemParam;
    }

    public SystemParam getDirectionSystemParam() {
        return directionSystemParam;
    }

    public void setDirectionSystemParam(SystemParam directionSystemParam) {
        this.directionSystemParam = directionSystemParam;
    }

    public SystemParam getLabelSystenParam() {
        return labelSystenParam;
    }

    public void setLabelSystenParam(SystemParam labelSystenParam) {
        this.labelSystenParam = labelSystenParam;
    }

    public void setIntervalSystemParam(SystemParam intervalSystemParam) {
        this.intervalSystemParam = intervalSystemParam;
    }

    public SSOHeaderDescription getSsoHeaderDescription() {
        return ssoHeaderDescription;
    }

    public void setUserPermission(Permisstions userPermission) {
        this.userPermission = userPermission;
    }

    public Permisstions getUserPermission() {
        return userPermission;
    }

    public void setHeaderDescription(AHeaderDescription headerDescription) {
        this.headerDescription = headerDescription;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setOpenDataType(int openDataType) {
        this.openDataType = openDataType;
    }

    public int getOpenDataType() {
        return openDataType;
    }

    public AHeaderDescription getHeaderDescription() {
        return headerDescription;
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
