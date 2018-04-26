package com.centanet.hk.aplus.bean.login;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/14.
 */

public class UserPermission {

    List<PermisstionUserInfo> PermisstionUserInfo;
    boolean Flag;
    String ErrorMsg;
    String RunTime;

    public List<com.centanet.hk.aplus.bean.login.PermisstionUserInfo> getPermisstionUserInfo() {
        return PermisstionUserInfo;
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

    public void setPermisstionUserInfo(List<com.centanet.hk.aplus.bean.login.PermisstionUserInfo> permisstionUserInfo) {
        PermisstionUserInfo = permisstionUserInfo;
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

    @Override
    public String toString() {
        return "UserPermission{" +
                "PermisstionUserInfo=" + PermisstionUserInfo +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
