package com.centanet.hk.aplus.entity.http;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class AutoSearchDescription {

    private boolean IsMobileRequest;
    private boolean IsVoice;
    private String Name;

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    public boolean isVoice() {
        return IsVoice;
    }

    public String getName() {
        return Name;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public void setVoice(boolean voice) {
        IsVoice = voice;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "AutoSearchDescription{" +
                "IsMobileRequest=" + IsMobileRequest +
                ", IsVoice=" + IsVoice +
                ", Name='" + Name + '\'' +
                '}';
    }
}
