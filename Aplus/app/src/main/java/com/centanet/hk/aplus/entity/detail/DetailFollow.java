package com.centanet.hk.aplus.entity.detail;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/9.
 */

public class DetailFollow {

    private List<PropertyFollow> PropertyFollows;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public void setPropertyFollows(List<PropertyFollow> propertyFollows) {
        PropertyFollows = propertyFollows;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    public List<PropertyFollow> getPropertyFollows() {
        return PropertyFollows;
    }

    public boolean isFlag() {
        return Flag;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public String getRunTime() {
        return RunTime;
    }

    @Override
    public String toString() {
        return "DetailFollowData{" +
                "PropertyFollows=" + PropertyFollows +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
