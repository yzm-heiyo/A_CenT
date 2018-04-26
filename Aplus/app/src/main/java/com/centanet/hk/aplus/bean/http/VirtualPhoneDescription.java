package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/4/13.
 */

public class VirtualPhoneDescription {

    private String StaffNo;
    private String TargetMobileNumber;
    private String System;
    private String ItemType;
    private String ItemKeyId;

    public void setStaffNo(String staffNo) {
        StaffNo = staffNo;
    }

    public void setTargetMobileNumber(String targetMobileNumber) {
        TargetMobileNumber = targetMobileNumber;
    }

    public void setSystem(int system) {
        System = system+"";
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public void setItemKeyId(String itemKeyId) {
        ItemKeyId = itemKeyId;
    }

    public String getStaffNo() {
        return StaffNo;
    }

    public String getTargetMobileNumber() {
        return TargetMobileNumber;
    }

    public String getSystem() {
        return System;
    }

    public String getItemType() {
        return ItemType;
    }

    public String getItemKeyId() {
        return ItemKeyId;
    }
}
