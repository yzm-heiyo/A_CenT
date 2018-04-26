package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class SSOLoginDescription {

    private String DomainAccount = "T1258";
    private String DomainPass = "a123456";

    public String getDomainAccount() {
        return DomainAccount;
    }

    public String getDomainPass() {
        return DomainPass;
    }

    public void setDomainAccount(String domainAccount) {
        DomainAccount = domainAccount;
    }

    public void setDomainPass(String domainPass) {
        DomainPass = domainPass;
    }
}
