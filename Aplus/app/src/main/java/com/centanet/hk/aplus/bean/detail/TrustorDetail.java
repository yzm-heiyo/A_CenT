package com.centanet.hk.aplus.bean.detail;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class TrustorDetail implements Serializable{

    private String PropertyKeyId;          /** 房源ID */
    private String TrustorKeyId;           /** 委托人ID */
    private String ContactValue;          /** 电话、邮件 */
    private String ContactTypeKeyId;      /** 电话类型 从系统参数获取 */
    private String ContactTypeName;       /** 电话类型名称 */
    private String MobileRemark;          /** 备注 */
    private int DirectSellType;            /** 直销类型 0：不反对 1：不可以 2：未知 */
    private boolean IsHidden;             /** 是否隐藏联系方式 */
    private String SetHiddenUserKeyId;    /** 设置隐藏人KeyId */
    private String SetHiddenUserDeptKeyId;/** 设置隐藏部门KeyId */
    private String DetailsCreateTime;     /** 业主联系方式创建时间 */
    private String DetailsCreator;        /** 业主联系方式创建人 */
    private String DetailsUpdateTime;     /** 业主联系方式更新时间 */
    private String DetailsUpdator;        /** 业主联系方式修改人 */
    private String KeyId;

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

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
        DirectSellType = directSellTyp;
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

    public String getKeyId() {
        return KeyId;
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
        return DirectSellType;
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
                ", DirectSellTyp=" + DirectSellType +
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
