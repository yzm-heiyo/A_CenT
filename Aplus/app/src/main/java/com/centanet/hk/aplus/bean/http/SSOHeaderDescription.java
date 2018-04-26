package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class SSOHeaderDescription {

    private String Udid;
    private String ClientVer = "1.1.0";
    private String Channel = "none";
    private String platform = "Android";
    private String UserAgent = android.os.Build.BRAND;
    private String Accept = "application/json";
    private String HKSession;
    private String CompanyCode;

    public void setUdid(String udid) {
        Udid = udid;
    }

    public void setClientVer(String clientVer) {
        ClientVer = clientVer;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public void setAccept(String accept) {
        Accept = accept;
    }

    public void setHKSession(String HKSession) {
        this.HKSession = HKSession;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getUdid() {
        return Udid;
    }

    public String getClientVer() {
        return ClientVer;
    }

    public String getChannel() {
        return Channel;
    }

    public String getPlatform() {
        return platform;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public String getAccept() {
        return Accept;
    }

    public String getHKSession() {
        return HKSession;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    @Override
    public String toString() {
        return "SSOHeaderDescription{" +
                "Udid='" + Udid + '\'' +
                ", ClientVer='" + ClientVer + '\'' +
                ", Channel='" + Channel + '\'' +
                ", platform='" + platform + '\'' +
                ", UserAgent='" + UserAgent + '\'' +
                ", Accept='" + Accept + '\'' +
                ", HKSession='" + HKSession + '\'' +
                ", CompanyCode='" + CompanyCode + '\'' +
                '}';
    }
}
