package com.centanet.hk.aplus.Views.LoginView.persent;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.LoginView.model.ILoginModel;
import com.centanet.hk.aplus.Views.LoginView.model.LoginModel;
import com.centanet.hk.aplus.Views.LoginView.view.ILoginView;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.PermissionDescription;
import com.centanet.hk.aplus.entity.http.SSOHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOHomeConfigDescription;
import com.centanet.hk.aplus.entity.http.SSOLoginDescription;
import com.centanet.hk.aplus.entity.login.HomeConfig;
import com.centanet.hk.aplus.entity.login.Login;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class LoginPresent implements ILoginPresent {

    private ILoginView loginView;
    private ILoginModel loginModel;
    private SSOHeaderDescription headerDescription;
    private SSOLoginDescription loginDescription;
    private String staffNo;

    public LoginPresent(ILoginView loginView) {
        loginModel = LoginModel.getInstance();
        loginModel.setLisenter(listener);
        headerDescription= new SSOHeaderDescription();
        this.loginView = loginView;
    }

    private LoginModel.OnReceiveLisenter listener = new LoginModel.OnReceiveLisenter() {

        @Override
        public void OnGetConfig(HomeConfig config) {
            String[] url = config.getResult().get(2).getJumpContent().split(":");
            HttpUtil.URL = url[1].substring(1)+":"+url[2].substring(0,url[2].length()-2);
            PermissionDescription permissionDescription = new PermissionDescription();
            List<String> staffNos = new ArrayList<>();
            staffNos.add(staffNo);

            permissionDescription.setUserNumbers(staffNos);
            loginModel.doPost(HttpUtil.URL_PERMISSION,new AHeaderDescription(),permissionDescription);
        }

        @Override
        public void OnLogin(Login login) {
            staffNo = login.getResult().getDomainUser().getStaffNo();
            headerDescription.setHKSession(login.getResult().getSession());
            headerDescription.setCompanyCode(login.getResult().getDomainUser().getCityCode());
            loginModel.doGet(HttpUtil.URL_HomeConfig,headerDescription,new SSOHomeConfigDescription());
        }

        @Override
        public void Onfinish() {

        }
    };

    @Override
    public void login(SSOLoginDescription ssoLoginDescription) {
        loginDescription = ssoLoginDescription;
        loginModel.doPost(HttpUtil.URL_SSO,headerDescription,ssoLoginDescription);
    }
}
