package com.centanet.hk.aplus.Views.LoginView.view;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOHeaderDescription;
import com.centanet.hk.aplus.bean.login.Permisstions;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public interface ILoginView {

    /**
     * 保存header和permission
     *
     * @param headerDescription
     * @param permission
     */
    void reFreshApplication(AHeaderDescription headerDescription, Permisstions permission, SSOHeaderDescription ssoHeaderDescription);

    /**
     * 儅用戶已在其他手機綁定或者賬號密碼錯誤時候彈出提示框
     *
     * @param tips
     */
    void showTipDialog(String tips);

    void toLogin();

    void onFailure();

    void setUpdateUrl(String url, boolean isForceUpdate);
}
