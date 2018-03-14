package com.centanet.hk.aplus.entity.login;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class HomeConfigResult {

    private int JumpType;
    int DispIndex;
    boolean HomeShow;
    String Title;
    String IconUrl;
    String JumpContent;
    String ConfigId;
    String Description;

    public int getJumpType() {
        return JumpType;
    }

    public int getDispIndex() {
        return DispIndex;
    }

    public boolean isHomeShow() {
        return HomeShow;
    }

    public String getTitle() {
        return Title;
    }

    public String getIconUrl() {
        return IconUrl;
    }

    public String getJumpContent() {
        return JumpContent;
    }

    public String getConfigId() {
        return ConfigId;
    }

    public String getDescription() {
        return Description;
    }

    public void setJumpType(int jumpType) {
        JumpType = jumpType;
    }

    public void setDispIndex(int dispIndex) {
        DispIndex = dispIndex;
    }

    public void setHomeShow(boolean homeShow) {
        HomeShow = homeShow;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setIconUrl(String iconUrl) {
        IconUrl = iconUrl;
    }

    public void setJumpContent(String jumpContent) {
        JumpContent = jumpContent;
    }

    public void setConfigId(String configId) {
        ConfigId = configId;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "HomeConfigResult{" +
                "JumpType=" + JumpType +
                ", DispIndex=" + DispIndex +
                ", HomeShow=" + HomeShow +
                ", Title='" + Title + '\'' +
                ", IconUrl='" + IconUrl + '\'' +
                ", JumpContent='" + JumpContent + '\'' +
                ", ConfigId='" + ConfigId + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
