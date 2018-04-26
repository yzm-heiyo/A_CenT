package com.centanet.hk.aplus.bean.detail;

/**
 * Created by yangzm4 on 2018/3/9.
 */

public class PropertyFollow {

    private String KeyId;
    private String FollowTypeKeyId;
    private String FollowType;
    private String FollowTypeCode;
    private String FollowContent;
    private String Follower;
    private String FollowTime;
    private String FollowerKeyId;
    private String DepartmentName;
    private String DepartmentKeyId;
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

    public void setFollowTime(String followTime) {
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

    public String getFollowTime() {
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
        return "PropertyFollow{" +
                "KeyId='" + KeyId + '\'' +
                ", FollowTypeKeyId='" + FollowTypeKeyId + '\'' +
                ", FollowType='" + FollowType + '\'' +
                ", FollowTypeCode='" + FollowTypeCode + '\'' +
                ", FollowContent='" + FollowContent + '\'' +
                ", Follower='" + Follower + '\'' +
                ", FollowTime='" + FollowTime + '\'' +
                ", FollowerKeyId='" + FollowerKeyId + '\'' +
                ", DepartmentName='" + DepartmentName + '\'' +
                ", DepartmentKeyId='" + DepartmentKeyId + '\'' +
                ", PropertyKeyId='" + PropertyKeyId + '\'' +
                '}';
    }
}
