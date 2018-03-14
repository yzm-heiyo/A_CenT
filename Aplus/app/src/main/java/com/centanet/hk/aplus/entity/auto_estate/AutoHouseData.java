package com.centanet.hk.aplus.entity.auto_estate;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class AutoHouseData {
    private List<PropertyParamHints> PropertyParamHints;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public void setPropertyParamHints(List<com.centanet.hk.aplus.entity.auto_estate.PropertyParamHints> propertyParamHints) {
        PropertyParamHints = propertyParamHints;
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

    public List<com.centanet.hk.aplus.entity.auto_estate.PropertyParamHints> getPropertyParamHints() {
        return PropertyParamHints;
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
        return "AutoHouseData{" +
                "PropertyParamHints=" + PropertyParamHints +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
