package com.centanet.hk.aplus.Views.LoginView.view;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public interface ILoginView {

    void reFreshApplication(AHeaderDescription headerDescription);
    void showTipDialog(String tips);
    void toLogin();
}
