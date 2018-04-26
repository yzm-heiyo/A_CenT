package com.centanet.hk.aplus.bean.login;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class Login {

    private int RCode;
    private String RMessage;
    private String Total;
    private LoginResult Result;

    public void setResult(LoginResult result) {
        Result = result;
    }

    public LoginResult getResult() {
        return Result;
    }

    public void setRCode(int RCode) {
        this.RCode = RCode;
    }

    public void setRMessage(String RMessage) {
        this.RMessage = RMessage;
    }

    public void setTotal(String total) {
        Total = total;
    }


    public int getRCode() {
        return RCode;
    }

    public String getRMessage() {
        return RMessage;
    }

    public String getTotal() {
        return Total;
    }

    @Override
    public String toString() {
        return "Login{" +
                "RCode=" + RCode +
                ", RMessage='" + RMessage + '\'' +
                ", Total='" + Total + '\'' +
                ", Result=" + Result +
                '}';
    }
}
