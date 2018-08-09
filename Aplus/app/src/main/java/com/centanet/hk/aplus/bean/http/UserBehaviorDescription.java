package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/6/25.
 */

public class UserBehaviorDescription {

    private int Action; /** 1：ScreenCapture 2：View 3：Click **/
    private int Page;  /** 1：Property.Detail.Basic 2：Property.Detail.Follow 3：Property.Detail.Trustor 4：Property.List **/
    private String Extras; /** Json **/
    private boolean IsMobileRequest = true;

    public void setAction(int action) {
        Action = action;
    }

    public void setPage(int page) {
        Page = page;
    }

    public void setExtras(String extras) {
        Extras = extras;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public int getAction() {
        return Action;
    }

    public int getPage() {
        return Page;
    }

    public String getExtras() {
        return Extras;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    @Override
    public String toString() {
        return "UserBehaviorDescription{" +
                "Action=" + Action +
                ", Page=" + Page +
                ", Extras='" + Extras + '\'' +
                ", IsMobileRequest=" + IsMobileRequest +
                '}';
    }
}
