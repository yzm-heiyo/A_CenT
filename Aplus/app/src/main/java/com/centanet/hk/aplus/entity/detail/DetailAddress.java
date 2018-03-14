package com.centanet.hk.aplus.entity.detail;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailAddress {
    private String DetailAddressChInfo;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public void setDetailAddressChInfo(String detailAddressChInfo) {
        DetailAddressChInfo = detailAddressChInfo;
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

    public String getDetailAddressChInfo() {
        return DetailAddressChInfo;
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
