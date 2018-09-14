package com.centanet.hk.aplus.bean.http;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class AutoSearchDescription {

    private boolean IsMobileRequest;
    private boolean IsVoice;
    private String Name;
    private List<String> DistrictKeyIds;

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    public boolean isVoice() {
        return IsVoice;
    }

    public String getName() {
        return Name;
    }

    public void setDistrictKeyIds(List<String> districtKeyIds) {
        DistrictKeyIds = districtKeyIds;
    }

    public List<String> getDistrictKeyIds() {
        return DistrictKeyIds;
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
