package com.centanet.hk.aplus.bean.favo;

/**
 * Created by yangzm4 on 2018/3/28.
 */

public class FavoResponse {

    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

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
}
