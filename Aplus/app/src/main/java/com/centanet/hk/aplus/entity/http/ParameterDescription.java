package com.centanet.hk.aplus.entity.http;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class ParameterDescription {

    private String UpdateTime = "2010-01-01T00:00:00";
    private boolean IsMobileRequest=true;

    public ParameterDescription() {
    }

    public ParameterDescription(String updateTime, boolean isMobileRequest) {
        UpdateTime = updateTime;
        IsMobileRequest = isMobileRequest;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }
}
