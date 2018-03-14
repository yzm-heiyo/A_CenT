package com.centanet.hk.aplus.entity.http;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class AHeaderDescription {

    private String token = "";
//            "ctXla6G99uaXiI/Ub19VPqchGJradzYQxXZcRf24k57gBf2YC0tba/wmxNtRX5HtQB+XL3HM" +
//            "0pFMsaOHt38E4BOKIHFmdS1t95HHOnipbbT50+HjxmQPehJnvg80LC7ooPlRPoueRR9NcWJdBtyGSzGmOEFsOCfSBp8W5Tl9GMxmV" +
//            "4uKGXT4x7b3Ol/4HKkS/WfhosPkixDEqVaLh26HfeNpTsrz1cEf6+AOst2rYLkvf46lnLidnWPPpg1EwvoQBZSpKUkALMI=";
    private String Accept = "application/json";
    private String number = "1516084328";
    private String platform = "iOS";
    private String sign = "f1425a22ce00da4d2bda657943da357b";
    private String staffno = "T1248";
    private String ClientVer = "1.0.1";
    private String UserAgent = "iPhone";

    public AHeaderDescription() {
    }

    public AHeaderDescription(String token, String accept, String number, String platform, String sign, String staffno, String clientVer, String userAgent) {
        this.token = token;
        Accept = accept;
        this.number = number;
        this.platform = platform;
        this.sign = sign;
        this.staffno = staffno;
        ClientVer = clientVer;
        UserAgent = userAgent;
    }

    public void setUserAgent(String userAgent) {
        UserAgent = userAgent;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAccept(String accept) {
        Accept = accept;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setStaffno(String staffno) {
        this.staffno = staffno;
    }

    public void setClientVer(String clientVer) {
        ClientVer = clientVer;
    }

    public String getToken() {
        return token;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public String getAccept() {
        return Accept;
    }

    public String getNumber() {
        return number;
    }

    public String getPlatform() {
        return platform;
    }

    public String getSign() {
        return sign;
    }

    public String getStaffno() {
        return staffno;
    }

    public String getClientVer() {
        return ClientVer;
    }

}
