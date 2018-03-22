package com.centanet.hk.aplus.Views.LoginView.persent;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.LoginView.model.ILoginModel;
import com.centanet.hk.aplus.Views.LoginView.model.LoginModel;
import com.centanet.hk.aplus.Views.LoginView.view.ILoginView;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.ParameterDescription;
import com.centanet.hk.aplus.entity.http.PermissionDescription;
import com.centanet.hk.aplus.entity.http.SSOHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOHomeConfigDescription;
import com.centanet.hk.aplus.entity.http.SSOLoginDescription;
import com.centanet.hk.aplus.entity.login.HomeConfig;
import com.centanet.hk.aplus.entity.login.Login;
import com.centanet.hk.aplus.entity.login.Permisstions;
import com.centanet.hk.aplus.entity.login.UserPermission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class LoginPresent implements ILoginPresent {

    private ILoginView loginView;
    private ILoginModel loginModel;
    private SSOHeaderDescription ssoHeader;
    private String staffNo;
    private Login loginData;
    private AHeaderDescription headerDescription;
    private Permisstions userPermission;

    public LoginPresent(ILoginView loginView) {
        loginModel = LoginModel.getInstance();
        loginModel.setLisenter(listener);
        headerDescription = new AHeaderDescription();
        this.loginView = loginView;
    }

    private LoginModel.OnReceiveLisenter listener = new LoginModel.OnReceiveLisenter() {

        @Override
        public void OnPermissionFinish(UserPermission permission) {
            headerDescription.setToken(permission.getPermisstionUserInfo().get(0).getAccountInfo());
            headerDescription.setStaffno(staffNo);
            String number = new Date().getTime() + "";
            headerDescription.setNumber(number);
            headerDescription.setSign(MD5Util.getMD5Str(headerDescription.getSign() + number + staffNo));
            userPermission = permission.getPermisstionUserInfo().get(0).getPermisstions();
            loginModel.doPost(HttpUtil.URL_PARAMETER,headerDescription,new ParameterDescription());
        }

        @Override
        public void OnGetConfig(HomeConfig config) {

            HttpUtil.URL = parseJumpContent(config.getResult().get(2).getJumpContent());
            HttpUtil.URL_USERLAUSE = parseJumpContent(config.getResult().get(0).getJumpContent());
            PermissionDescription permissionDescription = new PermissionDescription();
            List<String> staffNos = new ArrayList<>();
            config.getResult().get(0).getJumpContent().split(":");
            staffNos.add(staffNo);
            permissionDescription.setUserNumbers(staffNos);
            loginModel.doPost(HttpUtil.URL_PERMISSION, headerDescription, permissionDescription);
        }

        @Override
        public void OnLogin(Login login) {
            loginData = login;
            if (login.getResult() == null) {
                Onfinish();
                return;
            }
            staffNo = login.getResult().getDomainUser().getStaffNo();
            ssoHeader.setHKSession(login.getResult().getSession());
            ssoHeader.setCompanyCode(login.getResult().getDomainUser().getCityCode());

            loginModel.doGet(HttpUtil.URL_HomeConfig, ssoHeader, new SSOHomeConfigDescription());
        }

        @Override
        public void Onfinish() {
            if (loginData.getResult() == null) {
                loginView.showTipDialog(loginData.getRMessage());
                return;
            }
            loginView.reFreshApplication(headerDescription,userPermission,ssoHeader);
            loginView.toLogin();
        }
    };

    private String parseJumpContent(String param){
        String[] url = param.split(":");
        return url[1].substring(1) + ":" + url[2].substring(0, url[2].length() - 2);
    }


    @Override
    public void login(SSOHeaderDescription ssoHeaderDescription,SSOLoginDescription ssoLoginDescription) {
        ssoHeader = ssoHeaderDescription;
        ssoHeader.setUdid(loginModel.getUniquePsuedoID());
        loginModel.doPost(HttpUtil.URL_SSO, ssoHeaderDescription, ssoLoginDescription);
    }
}
