package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/7/3.
 */

public class DetailListsDescription {

    private String cuntCode;
    private String keyId;
    private boolean isMobileRequest = true;

    public void setCuntCode(String cuntCode) {
        this.cuntCode = cuntCode;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public void setMobileRequest(boolean mobileRequest) {
        isMobileRequest = mobileRequest;
    }

    public String getCuntCode() {
        return cuntCode;
    }

    public String getKeyId() {
        return keyId;
    }

    public boolean isMobileRequest() {
        return isMobileRequest;
    }

    @Override
    public String toString() {
        return "DetailListsDescription{" +
                "cuntCode='" + cuntCode + '\'' +
                ", keyId='" + keyId + '\'' +
                ", isMobileRequest=" + isMobileRequest +
                '}';
    }
}
