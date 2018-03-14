package com.centanet.hk.aplus.entity.http;

/**
 * Created by yangzm4 on 2018/3/7.
 */

public class FavoriteDescription {

    private String KeyId;
    private boolean IsMobileRequest = true;

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getKeyId() {
        return KeyId;
    }

    @Override
    public String toString() {
        return "FavoriteDescription{" +
                "KeyId='" + KeyId + '\'' +
                ", IsMobileRequest=" + IsMobileRequest +
                '}';
    }
}
