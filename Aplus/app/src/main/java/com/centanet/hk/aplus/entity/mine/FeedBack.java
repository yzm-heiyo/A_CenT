package com.centanet.hk.aplus.entity.mine;

/**
 * Created by yangzm4 on 2018/3/21.
 */

public class FeedBack {

    private String RMessage;
    private String RCode;
    private String Result;
    private String Total;

    public void setRMessage(String RMessage) {
        this.RMessage = RMessage;
    }

    public void setRCode(String RCode) {
        this.RCode = RCode;
    }

    public void setResult(String result) {
        Result = result;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getRMessage() {
        return RMessage;
    }

    public String getRCode() {
        return RCode;
    }

    public String getResult() {
        return Result;
    }

    public String getTotal() {
        return Total;
    }
}
