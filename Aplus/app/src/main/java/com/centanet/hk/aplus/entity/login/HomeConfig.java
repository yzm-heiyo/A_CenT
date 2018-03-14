package com.centanet.hk.aplus.entity.login;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class HomeConfig {

    String RMessage;
    int RCode;
    String Total;
    List<HomeConfigResult> Result;

    public void setRMessage(String RMessage) {
        this.RMessage = RMessage;
    }

    public void setRCode(int RCode) {
        this.RCode = RCode;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public void setResult(List<HomeConfigResult> result) {
        Result = result;
    }

    public String getRMessage() {
        return RMessage;
    }

    public int getRCode() {
        return RCode;
    }

    public String getTotal() {
        return Total;
    }

    public List<HomeConfigResult> getResult() {
        return Result;
    }

    @Override
    public String toString() {
        return "HomeConfig{" +
                "RMessage='" + RMessage + '\'' +
                ", RCode=" + RCode +
                ", Total='" + Total + '\'' +
                ", Result=" + Result +
                '}';
    }
}
