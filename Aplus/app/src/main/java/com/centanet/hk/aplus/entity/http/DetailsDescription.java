package com.centanet.hk.aplus.entity.http;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailsDescription {
    private String KeyId;
    private boolean IsMobileRequest = true;

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public String getKeyId() {
        return KeyId;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }
}
