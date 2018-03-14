package com.centanet.hk.aplus.entity.http;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/14.
 */

public class PermissionDescription {

    private List<String> UserNumbers;
    private boolean IsMobileRequest;

    public void setUserNumbers(List<String> userNumbers) {
        UserNumbers = userNumbers;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public List<String> getUserNumbers() {
        return UserNumbers;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    @Override
    public String toString() {
        return "PermissionDescription{" +
                "UserNumbers=" + UserNumbers +
                ", IsMobileRequest=" + IsMobileRequest +
                '}';
    }
}
