package com.centanet.hk.aplus.entity.update;

/**
 * Created by yangzm4 on 2018/4/3.
 */

public class UpdateParams {

    String RMessage;
    int RCode;
    UpdateResult Result;
    int Total;

    public void setRMessage(String RMessage) {
        this.RMessage = RMessage;
    }

    public void setRCode(int RCode) {
        this.RCode = RCode;
    }

    public void setResult(UpdateResult result) {
        Result = result;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public String getRMessage() {
        return RMessage;
    }

    public int getRCode() {
        return RCode;
    }

    public UpdateResult getResult() {
        return Result;
    }

    public int getTotal() {
        return Total;
    }
}
