package com.centanet.hk.aplus.entity.login;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class LoginResult {

    private String Session;
    private DomainUser DomainUser;

    public void setSession(String session) {
        Session = session;
    }

    public void setDomainUser(com.centanet.hk.aplus.entity.login.DomainUser domainUser) {
        DomainUser = domainUser;
    }

    public String getSession() {
        return Session;
    }

    public com.centanet.hk.aplus.entity.login.DomainUser getDomainUser() {
        return DomainUser;
    }


    @Override
    public String toString() {
        return "LoginResult{" +
                "Session='" + Session + '\'' +
                ", DomainUser=" + DomainUser +
                '}';
    }
}
