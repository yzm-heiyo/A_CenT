package com.centanet.hk.aplus.entity.detail;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

class Trustor {

    private String KeyId;
    private String Creator;
    private String CreateTime;
    private String TrustortRemark;
    private String TrustorGender;
    private String TrustorGenderKeyId;
    private String TrustorTypeName;
    private String TrustorTypeKeyId;
    private String TrustorName;
    private String PropertyKeyId;
    private String TrustorKeyId;
    private List<TrustorDetail> TrustorDetails;

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setTrustortRemark(String trustortRemark) {
        TrustortRemark = trustortRemark;
    }

    public void setTrustorGender(String trustorGender) {
        TrustorGender = trustorGender;
    }

    public void setTrustorGenderKeyId(String trustorGenderKeyId) {
        TrustorGenderKeyId = trustorGenderKeyId;
    }

    public void setTrustorTypeName(String trustorTypeName) {
        TrustorTypeName = trustorTypeName;
    }

    public void setTrustorTypeKeyId(String trustorTypeKeyId) {
        TrustorTypeKeyId = trustorTypeKeyId;
    }

    public void setTrustorName(String trustorName) {
        TrustorName = trustorName;
    }

    public void setPropertyKeyId(String propertyKeyId) {
        PropertyKeyId = propertyKeyId;
    }

    public void setTrustorKeyId(String trustorKeyId) {
        TrustorKeyId = trustorKeyId;
    }

    public void setTrustorDetails(List<TrustorDetail> trustorDetails) {
        TrustorDetails = trustorDetails;
    }

    public String getKeyId() {
        return KeyId;
    }

    public String getCreator() {
        return Creator;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getTrustortRemark() {
        return TrustortRemark;
    }

    public String getTrustorGender() {
        return TrustorGender;
    }

    public String getTrustorGenderKeyId() {
        return TrustorGenderKeyId;
    }

    public String getTrustorTypeName() {
        return TrustorTypeName;
    }

    public String getTrustorTypeKeyId() {
        return TrustorTypeKeyId;
    }

    public String getTrustorName() {
        return TrustorName;
    }

    public String getPropertyKeyId() {
        return PropertyKeyId;
    }

    public String getTrustorKeyId() {
        return TrustorKeyId;
    }

    public List<TrustorDetail> getTrustorDetails() {
        return TrustorDetails;
    }

    @Override
    public String toString() {
        return "Trustor{" +
                "KeyId='" + KeyId + '\'' +
                ", Creator='" + Creator + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", TrustortRemark='" + TrustortRemark + '\'' +
                ", TrustorGender='" + TrustorGender + '\'' +
                ", TrustorGenderKeyId='" + TrustorGenderKeyId + '\'' +
                ", TrustorTypeName='" + TrustorTypeName + '\'' +
                ", TrustorTypeKeyId='" + TrustorTypeKeyId + '\'' +
                ", TrustorName='" + TrustorName + '\'' +
                ", PropertyKeyId='" + PropertyKeyId + '\'' +
                ", TrustorKeyId='" + TrustorKeyId + '\'' +
                ", TrustorDetails=" + TrustorDetails +
                '}';
    }
}
