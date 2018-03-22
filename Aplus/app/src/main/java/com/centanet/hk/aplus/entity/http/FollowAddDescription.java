package com.centanet.hk.aplus.entity.http;

import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.FeedBackType.FOLLOW_GENERAL;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public class FollowAddDescription {
    private String PropertyKeyId;
    private int FollowType = FOLLOW_GENERAL;
    private List<String> ContactsName;
    private List<String> InformDepartsName;
    private String FollowContent;
    private String Mobile;
    private String TrustorName;
    private String TrustorTypeKeyId;
    private String TrustorGenderKeyId;
    private String TrustorRemark;
    private String telphone1;
    private String telphone2;
    private String telphone3;
    private String TrustType;
    private String OpeningType;
    private String OpeningPersonName;
    private String OpeningDepName;
    private String RentPrice;
    private String RentPer;
    private String SalePrice;
    private String SalePer;
    private String OpeningPersonKeyId;
    private String OpeningDepKeyId;
    private String TargetDepartmentKeyId;
    private String TargetUserKeyId;
    private List<String> MsgUserKeyIds;
    private List<String> MsgDeptKeyIds;
    private String MsgTime;
    private String KeyId;
    private boolean IsMobileRequest = true;

    public FollowAddDescription(String propertyKeyId, int followType, List<String> contactsName, List<String> informDepartsName, String followContent, String mobile, String trustorName, String trustorTypeKeyId, String trustorGenderKeyId, String trustorRemark, String telphone1, String telphone2, String telphone3, String trustType, String openingType, String openingPersonName, String openingDepName, String rentPrice, String rentPer, String salePrice, String salePer, String openingPersonKeyId, String openingDepKeyId, String targetDepartmentKeyId, String targetUserKeyId, List<String> msgUserKeyIds, List<String> msgDeptKeyIds, String msgTime, String keyId, boolean isMobileRequest) {
        PropertyKeyId = propertyKeyId;
        FollowType = followType;
        ContactsName = contactsName;
        InformDepartsName = informDepartsName;
        FollowContent = followContent;
        Mobile = mobile;
        TrustorName = trustorName;
        TrustorTypeKeyId = trustorTypeKeyId;
        TrustorGenderKeyId = trustorGenderKeyId;
        TrustorRemark = trustorRemark;
        this.telphone1 = telphone1;
        this.telphone2 = telphone2;
        this.telphone3 = telphone3;
        TrustType = trustType;
        OpeningType = openingType;
        OpeningPersonName = openingPersonName;
        OpeningDepName = openingDepName;
        RentPrice = rentPrice;
        RentPer = rentPer;
        SalePrice = salePrice;
        SalePer = salePer;
        OpeningPersonKeyId = openingPersonKeyId;
        OpeningDepKeyId = openingDepKeyId;
        TargetDepartmentKeyId = targetDepartmentKeyId;
        TargetUserKeyId = targetUserKeyId;
        MsgUserKeyIds = msgUserKeyIds;
        MsgDeptKeyIds = msgDeptKeyIds;
        MsgTime = msgTime;
        KeyId = keyId;
        IsMobileRequest = isMobileRequest;
    }

    public FollowAddDescription() {

    }

    public void setPropertyKeyId(String propertyKeyId) {
        PropertyKeyId = propertyKeyId;
    }

    public void setFollowType(int followType) {
        FollowType = followType;
    }

    public void setContactsName(List<String> contactsName) {
        ContactsName = contactsName;
    }

    public void setInformDepartsName(List<String> informDepartsName) {
        InformDepartsName = informDepartsName;
    }

    public void setFollowContent(String followContent) {
        FollowContent = followContent;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setTrustorName(String trustorName) {
        TrustorName = trustorName;
    }

    public void setTrustorTypeKeyId(String trustorTypeKeyId) {
        TrustorTypeKeyId = trustorTypeKeyId;
    }

    public void setTrustorGenderKeyId(String trustorGenderKeyId) {
        TrustorGenderKeyId = trustorGenderKeyId;
    }

    public void setTrustorRemark(String trustorRemark) {
        TrustorRemark = trustorRemark;
    }

    public void setTelphone1(String telphone1) {
        this.telphone1 = telphone1;
    }

    public void setTelphone2(String telphone2) {
        this.telphone2 = telphone2;
    }

    public void setTelphone3(String telphone3) {
        this.telphone3 = telphone3;
    }

    public void setTrustType(String trustType) {
        TrustType = trustType;
    }

    public void setOpeningType(String openingType) {
        OpeningType = openingType;
    }

    public void setOpeningPersonName(String openingPersonName) {
        OpeningPersonName = openingPersonName;
    }

    public void setOpeningDepName(String openingDepName) {
        OpeningDepName = openingDepName;
    }

    public void setRentPrice(String rentPrice) {
        RentPrice = rentPrice;
    }

    public void setRentPer(String rentPer) {
        RentPer = rentPer;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public void setSalePer(String salePer) {
        SalePer = salePer;
    }

    public void setOpeningPersonKeyId(String openingPersonKeyId) {
        OpeningPersonKeyId = openingPersonKeyId;
    }

    public void setOpeningDepKeyId(String openingDepKeyId) {
        OpeningDepKeyId = openingDepKeyId;
    }

    public void setTargetDepartmentKeyId(String targetDepartmentKeyId) {
        TargetDepartmentKeyId = targetDepartmentKeyId;
    }

    public void setTargetUserKeyId(String targetUserKeyId) {
        TargetUserKeyId = targetUserKeyId;
    }

    public void setMsgUserKeyIds(List<String> msgUserKeyIds) {
        MsgUserKeyIds = msgUserKeyIds;
    }

    public void setMsgDeptKeyIds(List<String> msgDeptKeyIds) {
        MsgDeptKeyIds = msgDeptKeyIds;
    }

    public void setMsgTime(String msgTime) {
        MsgTime = msgTime;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public String getPropertyKeyId() {
        return PropertyKeyId;
    }

    public int getFollowType() {
        return FollowType;
    }

    public List<String> getContactsName() {
        return ContactsName;
    }

    public List<String> getInformDepartsName() {
        return InformDepartsName;
    }

    public String getFollowContent() {
        return FollowContent;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getTrustorName() {
        return TrustorName;
    }

    public String getTrustorTypeKeyId() {
        return TrustorTypeKeyId;
    }

    public String getTrustorGenderKeyId() {
        return TrustorGenderKeyId;
    }

    public String getTrustorRemark() {
        return TrustorRemark;
    }

    public String getTelphone1() {
        return telphone1;
    }

    public String getTelphone2() {
        return telphone2;
    }

    public String getTelphone3() {
        return telphone3;
    }

    public String getTrustType() {
        return TrustType;
    }

    public String getOpeningType() {
        return OpeningType;
    }

    public String getOpeningPersonName() {
        return OpeningPersonName;
    }

    public String getOpeningDepName() {
        return OpeningDepName;
    }

    public String getRentPrice() {
        return RentPrice;
    }

    public String getRentPer() {
        return RentPer;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public String getSalePer() {
        return SalePer;
    }

    public String getOpeningPersonKeyId() {
        return OpeningPersonKeyId;
    }

    public String getOpeningDepKeyId() {
        return OpeningDepKeyId;
    }

    public String getTargetDepartmentKeyId() {
        return TargetDepartmentKeyId;
    }

    public String getTargetUserKeyId() {
        return TargetUserKeyId;
    }

    public List<String> getMsgUserKeyIds() {
        return MsgUserKeyIds;
    }

    public List<String> getMsgDeptKeyIds() {
        return MsgDeptKeyIds;
    }

    public String getMsgTime() {
        return MsgTime;
    }

    public String getKeyId() {
        return KeyId;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }
}
