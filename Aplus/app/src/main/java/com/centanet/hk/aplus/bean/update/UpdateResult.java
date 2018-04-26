package com.centanet.hk.aplus.bean.update;

/**
 * Created by yangzm4 on 2018/4/3.
 */

public class UpdateResult {

    private int ClientVer;
    private String Platform;
    private int ForceUpdate;
    private int CompanyCode;
    private String UpdateContent;
    private String UpdateUrl;
    private String CreateTime;
    private String Channel;

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

    @Override
    public String toString() {
        return "UpdateResult{" +
                "ClientVer=" + ClientVer +
                ", Platform='" + Platform + '\'' +
                ", ForceUpdate=" + ForceUpdate +
                ", CompanyCode=" + CompanyCode +
                ", UpdateContent='" + UpdateContent + '\'' +
                ", UpdateUrl='" + UpdateUrl + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", Channel='" + Channel + '\'' +
                '}';
    }
}
