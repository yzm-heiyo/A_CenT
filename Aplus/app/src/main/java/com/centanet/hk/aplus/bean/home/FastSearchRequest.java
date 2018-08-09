package com.centanet.hk.aplus.bean.home;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class FastSearchRequest {

    private int PropertyType;

    public FastSearchRequest() {
    }

    public FastSearchRequest(int req) {
        this.PropertyType = req;
    }

    public void setReq(int req) {
        this.PropertyType = req;
    }

    public int getReq() {
        return PropertyType;
    }
}
