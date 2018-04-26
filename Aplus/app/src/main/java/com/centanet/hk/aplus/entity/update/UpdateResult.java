package com.centanet.hk.aplus.entity.update;

/**
 * Created by yangzm4 on 2018/4/3.
 */

public class UpdateResult {

    int ClientVer;
    String Platform;
    int ForceUpdate;
    int CompanyCode;
    String UpdateContent;
    String UpdateUrl;
    String CreateTime;
    String Channel;

    public void setClientVer(int clientVer) {
        ClientVer = clientVer;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public void setForceUpdate(int forceUpdate) {
        ForceUpdate = forceUpdate;
    }

    public void setCompanyCode(int companyCode) {
        CompanyCode = companyCode;
    }

    public void setUpdateContent(String updateContent) {
        UpdateContent = updateContent;
    }

    public void setUpdateUrl(String updateUrl) {
        UpdateUrl = updateUrl;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public int getClientVer() {
        return ClientVer;
    }

    public String getPlatform() {
        return Platform;
    }

    public int getForceUpdate() {
        return ForceUpdate;
    }

    public int getCompanyCode() {
        return CompanyCode;
    }

    public String getUpdateContent() {
        return UpdateContent;
    }

    public String getUpdateUrl() {
        return UpdateUrl;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getChannel() {
        return Channel;
    }
}
