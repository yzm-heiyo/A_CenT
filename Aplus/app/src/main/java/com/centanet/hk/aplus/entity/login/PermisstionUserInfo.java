package com.centanet.hk.aplus.entity.login;

/**
 * Created by yangzm4 on 2018/3/14.
 */

public class PermisstionUserInfo {

    Identify Identify;
    Permisstions Permisstions;
    String AccountInfo;

    public com.centanet.hk.aplus.entity.login.Identify getIdentify() {
        return Identify;
    }

    public com.centanet.hk.aplus.entity.login.Permisstions getPermisstions() {
        return Permisstions;
    }

    public String getAccountInfo() {
        return AccountInfo;
    }

    public void setIdentify(com.centanet.hk.aplus.entity.login.Identify identify) {
        Identify = identify;
    }

    public void setPermisstions(com.centanet.hk.aplus.entity.login.Permisstions permisstions) {
        Permisstions = permisstions;
    }

    public void setAccountInfo(String accountInfo) {
        AccountInfo = accountInfo;
    }


    @Override
    public String toString() {
        return "PermisstionUserInfo{" +
                "Identify=" + Identify +
                ", Permisstions=" + Permisstions +
                ", AccountInfo='" + AccountInfo + '\'' +
                '}';
    }
}
