package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class AHeaderDescription {

    private String token;
    private String Accept = "application/json";
    private String number;
    private String platform = "Android";
    private String sign = "CYDAP_com-group~Centa@";
    private String staffno;
    private String ClientVer = "1.0.1";
    private String UserAgent = android.os.Build.BRAND;
//    public static final String md5Header = "CYDAP_com-group~Centa@";

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

    @Override
    public String toString() {
        return "AHeaderDescription{" +
                "token='" + token + '\'' +
                ", Accept='" + Accept + '\'' +
                ", number='" + number + '\'' +
                ", platform='" + platform + '\'' +
                ", sign='" + sign + '\'' +
                ", staffno='" + staffno + '\'' +
                ", ClientVer='" + ClientVer + '\'' +
                ", UserAgent='" + UserAgent + '\'' +
                '}';
    }
}
