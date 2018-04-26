package com.centanet.hk.aplus.Views.LoginView.persent;

import com.centanet.hk.aplus.bean.http.SSOHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOLoginDescription;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public interface ILoginPresent {
    void login(SSOHeaderDescription ssoHeaderDescription,SSOLoginDescription ssoLoginDescription);
    void doGet(String address,Object header);
}
