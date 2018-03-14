package com.centanet.hk.aplus.Views.LoginView.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.LoginView.model.LoginModel;
import com.centanet.hk.aplus.Views.LoginView.persent.ILoginPresent;
import com.centanet.hk.aplus.Views.LoginView.persent.LoginPresent;
import com.centanet.hk.aplus.Views.Unensure.MainActivity;
import com.centanet.hk.aplus.Views.abst.LoginActivityAbst;
import com.centanet.hk.aplus.entity.http.SSOHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOHomeConfigDescription;
import com.centanet.hk.aplus.entity.http.SSOLoginDescription;
import com.centanet.hk.aplus.entity.login.Login;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends LoginActivityAbst implements LoginActivityAbst.ILoginManager,ILoginView {


    private ILoginPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = new LoginPresent(this);
    }

    @Override
    protected ILoginManager setLoginHelper() {
        return this;
    }


    @Override
    public void login(View v, String account, String password) {
//        startActivity(new Intent(this, MainActivity.class));
        present.login(new SSOLoginDescription());
    }
}
