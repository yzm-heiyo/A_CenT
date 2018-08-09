package com.centanet.hk.aplus.bean.detail;

import java.util.Date;

/**
 * Created by yangzm4 on 2018/7/2.
 */

public class PropertyBriefFollow {

    private String KeyId;               /** 跟進KeyId */
    private String FollowTypeKeyId;     /** 跟進類型 */
    private String FollowType;       /** 跟進類型，系統參數項名稱 */
    private String FollowTypeCode;   /** 跟進類型code，系統參數項名稱 */
    private String FollowContent;    /**跟進內容 */
    private String Follower;         /**跟進人 */
    private Date FollowTime ;        /**跟進時間,精度到秒 */
    private String FollowerKeyId;       /**跟進人KeyId */
    private String DepartmentName;   /**跟進人部門名稱 */
    private String DepartmentKeyId;     /**跟進人部門KeyId */
    private String PropertyKeyId;

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setFollowTypeKeyId(String followTypeKeyId) {
        FollowTypeKeyId = followTypeKeyId;
    }

    public void setFollowType(String followType) {
        FollowType = followType;
    }

    public void setFollowTypeCode(String followTypeCode) {
        FollowTypeCode = followTypeCode;
    }

    public void setFollowContent(String followContent) {
        FollowContent = followContent;
    }

    public void setFollower(String follower) {
        Follower = follower;
    }

    public void setFollowTime(Date followTime) {
        FollowTime = followTime;
    }

    public void setFollowerKeyId(String followerKeyId) {
        FollowerKeyId = followerKeyId;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public void setDepartmentKeyId(String departmentKeyId) {
        DepartmentKeyId = departmentKeyId;
    }

    public void setPropertyKeyId(String propertyKeyId) {
        PropertyKeyId = propertyKeyId;
    }

    /**房源KeyId */



    public String getKeyId() {
        return KeyId;
    }

    public String getFollowTypeKeyId() {
        return FollowTypeKeyId;
    }

    public String getFollowType() {
        return FollowType;
    }

    public String getFollowTypeCode() {
        return FollowTypeCode;
    }

    public String getFollowContent() {
        return FollowContent;
    }

    public String getFollower() {
        return Follower;
    }

    public Date getFollowTime() {
        return FollowTime;
    }

    public String getFollowerKeyId() {
        return FollowerKeyId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public String getDepartmentKeyId() {
        return DepartmentKeyId;
    }

    public String getPropertyKeyId() {
        return PropertyKeyId;
    }

    @Override
    public String toString() {
        return "PropertyBriefFollow{" +
                "KeyId='" + KeyId + '\'' +
                ", FollowTypeKeyId='" + FollowTypeKeyId + '\'' +
                ", FollowType='" + FollowType + '\'' +
                ", FollowTypeCode='" + FollowTypeCode + '\'' +
                ", FollowContent='" + FollowContent + '\'' +
                ", Follower='" + Follower + '\'' +
                ", FollowTime=" + FollowTime +
                ", FollowerKeyId='" + FollowerKeyId + '\'' +
                ", DepartmentName='" + DepartmentName + '\'' +
                ", DepartmentKeyId='" + DepartmentKeyId + '\'' +
                ", PropertyKeyId='" + PropertyKeyId + '\'' +
                '}';
    }
}
