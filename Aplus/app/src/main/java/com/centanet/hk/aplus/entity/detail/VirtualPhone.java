package com.centanet.hk.aplus.entity.detail;

/**
 * Created by yangzm4 on 2018/4/13.
 */

public class VirtualPhone {

    private String msisdn;
    private Object header;

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public Object getHeader() {
        return header;
    }
}
