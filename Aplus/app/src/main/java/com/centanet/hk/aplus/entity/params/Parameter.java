package com.centanet.hk.aplus.entity.params;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class Parameter implements Serializable {

    private String SystemParamNewUpdateTime;
    private boolean NeedUpdate;
    private List<SystemParam> SystemParam;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public Parameter() {
    }

    public Parameter(String systemParamNewUpdateTime, boolean needUpdate, List<com.centanet.hk.aplus.entity.params.SystemParam> systemParam, boolean flag, String errorMsg, String runTime) {
        SystemParamNewUpdateTime = systemParamNewUpdateTime;
        NeedUpdate = needUpdate;
        SystemParam = systemParam;
        Flag = flag;
        ErrorMsg = errorMsg;
        RunTime = runTime;
    }

    public void setSystemParamNewUpdateTime(String systemParamNewUpdateTime) {
        SystemParamNewUpdateTime = systemParamNewUpdateTime;
    }

    public void setNeedUpdate(boolean needUpdate) {
        NeedUpdate = needUpdate;
    }

    public void setSystemParam(List<com.centanet.hk.aplus.entity.params.SystemParam> systemParam) {
        SystemParam = systemParam;
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

    public String getSystemParamNewUpdateTime() {
        return SystemParamNewUpdateTime;
    }

    public boolean isNeedUpdate() {
        return NeedUpdate;
    }

    public List<com.centanet.hk.aplus.entity.params.SystemParam> getSystemParam() {
        return SystemParam;
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
}
