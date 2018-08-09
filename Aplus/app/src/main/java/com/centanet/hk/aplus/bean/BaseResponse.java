package com.centanet.hk.aplus.bean;

/**
 * Created by yangzm4 on 2018/7/25.
 */

public class BaseResponse {

    public boolean Flag;
    public String ErrorMsg;
    public String RunTime;

    public BaseResponse() {
    }

    public BaseResponse(boolean flag, String errorMsg, String runTime) {
        Flag = flag;
        ErrorMsg = errorMsg;
        RunTime = runTime;
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
        return "BaseResponse{" +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
