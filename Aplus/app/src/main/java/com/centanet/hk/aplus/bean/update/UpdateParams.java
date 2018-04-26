package com.centanet.hk.aplus.bean.update;

/**
 * Created by yangzm4 on 2018/4/3.
 */

public class UpdateParams {

    private String RMessage;
    private int RCode;
    private UpdateResult Result;
    private int Total;

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

    @Override
    public String toString() {
        return "UpdateParams{" +
                "RMessage='" + RMessage + '\'' +
                ", RCode=" + RCode +
                ", Result=" + Result +
                ", Total=" + Total +
                '}';
    }
}
