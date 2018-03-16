package com.centanet.hk.aplus.entity.detail;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class TrustorDetail {

    private String PropertyKeyId;
    private String TrustorKeyId;
    private String ContactValue;
    private String ContactTypeKeyId;
    private String ContactTypeName;
    private String MobileRemark;
    private int DirectSellTyp;
    private boolean IsHidden;
    private String SetHiddenUserKeyId;
    private String SetHiddenUserDeptKeyId;
    private String DetailsCreateTime;
    private String DetailsCreator;
    private String DetailsUpdateTime;
    private String DetailsUpdator;

    public void setPropertyKeyId(String propertyKeyId) {
        PropertyKeyId = propertyKeyId;
    }

    public void setTrustorKeyId(String trustorKeyId) {
        TrustorKeyId = trustorKeyId;
    }

    public void setContactValue(String contactValue) {
        ContactValue = contactValue;
    }

    public void setContactTypeKeyId(String contactTypeKeyId) {
        ContactTypeKeyId = contactTypeKeyId;
    }

    public void setContactTypeName(String contactTypeName) {
        ContactTypeName = contactTypeName;
    }

    public void setMobileRemark(String mobileRemark) {
        MobileRemark = mobileRemark;
    }

    public void setDirectSellTyp(int directSellTyp) {
        DirectSellTyp = directSellTyp;
    }

    public void setHidden(boolean hidden) {
        IsHidden = hidden;
    }

    public void setSetHiddenUserKeyId(String setHiddenUserKeyId) {
        SetHiddenUserKeyId = setHiddenUserKeyId;
    }

    public void setSetHiddenUserDeptKeyId(String setHiddenUserDeptKeyId) {
        SetHiddenUserDeptKeyId = setHiddenUserDeptKeyId;
    }

    public void setDetailsCreateTime(String detailsCreateTime) {
        DetailsCreateTime = detailsCreateTime;
    }

    public void setDetailsCreator(String detailsCreator) {
        DetailsCreator = detailsCreator;
    }

    public void setDetailsUpdateTime(String detailsUpdateTime) {
        DetailsUpdateTime = detailsUpdateTime;
    }

    public void setDetailsUpdator(String detailsUpdator) {
        DetailsUpdator = detailsUpdator;
    }

    public String getPropertyKeyId() {
        return PropertyKeyId;
    }

    public String getTrustorKeyId() {
        return TrustorKeyId;
    }

    public String getContactValue() {
        return ContactValue;
    }

    public String getContactTypeKeyId() {
        return ContactTypeKeyId;
    }

    public String getContactTypeName() {
        return ContactTypeName;
    }

    public String getMobileRemark() {
        return MobileRemark;
    }

    public int getDirectSellTyp() {
        return DirectSellTyp;
    }

    public boolean isHidden() {
        return IsHidden;
    }

    public String getSetHiddenUserKeyId() {
        return SetHiddenUserKeyId;
    }

    public String getSetHiddenUserDeptKeyId() {
        return SetHiddenUserDeptKeyId;
    }

    public String getDetailsCreateTime() {
        return DetailsCreateTime;
    }

    public String getDetailsCreator() {
        return DetailsCreator;
    }

    public String getDetailsUpdateTime() {
        return DetailsUpdateTime;
    }

    public String getDetailsUpdator() {
        return DetailsUpdator;
    }

    @Override
    public String toString() {
        return "TrustorDetail{" +
                "PropertyKeyId='" + PropertyKeyId + '\'' +
                ", TrustorKeyId='" + TrustorKeyId + '\'' +
                ", ContactValue='" + ContactValue + '\'' +
                ", ContactTypeKeyId='" + ContactTypeKeyId + '\'' +
                ", ContactTypeName='" + ContactTypeName + '\'' +
                ", MobileRemark='" + MobileRemark + '\'' +
                ", DirectSellTyp=" + DirectSellTyp +
                ", IsHidden=" + IsHidden +
                ", SetHiddenUserKeyId='" + SetHiddenUserKeyId + '\'' +
                ", SetHiddenUserDeptKeyId='" + SetHiddenUserDeptKeyId + '\'' +
                ", DetailsCreateTime='" + DetailsCreateTime + '\'' +
                ", DetailsCreator='" + DetailsCreator + '\'' +
                ", DetailsUpdateTime='" + DetailsUpdateTime + '\'' +
                ", DetailsUpdator='" + DetailsUpdator + '\'' +
                '}';
    }
}
