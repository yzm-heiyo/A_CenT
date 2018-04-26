package com.centanet.hk.aplus.bean.detail;

/**
 * Created by yangzm4 on 2018/4/13.
 */

public class VirtualPhone {

    private String msisdn;
    private Object header;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public Object getHeader() {
        return header;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public boolean isFlag() {
        return Flag;
    }

    public String getRunTime() {
        return RunTime;
    }
}
