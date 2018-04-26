package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class TrustorDescription {

    String KeyId;
    boolean IsMobileRequest = true;

    public String getKeyId() {
        return KeyId;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    @Override
    public String toString() {
        return "TrustorDescription{" +
                "KeyId='" + KeyId + '\'' +
                ", IsMobileRequest=" + IsMobileRequest +
                '}';
    }
}
