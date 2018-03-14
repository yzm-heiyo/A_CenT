package com.centanet.hk.aplus.Views.LoginView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.FeedBackView.model.FeedBackModel;
import com.centanet.hk.aplus.entity.http.SSOHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOLoginDescription;
import com.centanet.hk.aplus.entity.login.HomeConfig;
import com.centanet.hk.aplus.entity.login.Login;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class LoginModel implements ILoginModel {

    private static LoginModel loginModel = new LoginModel();
    private OnReceiveLisenter listener;

    public static synchronized LoginModel getInstance() {
        return loginModel;
    }

    @Override
    public void doGet(String address, Object header, Object params) {
        HttpUtil.doGet(address, header, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().toString();

                if (response.code() == 200) {
                    try {
                        HomeConfig config = GsonUtil.parseJson(dataBack, HomeConfig.class);
                        if (listener != null) listener.OnGetConfig(config);
                        L.d("Http_Config", config.toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void doPost(final String address, Object header, Object body) {
        String url = address;
        if (address != HttpUtil.URL_SSO && address != HttpUtil.URL_HomeConfig)
            url = HttpUtil.URL + address;
        HttpUtil.doPost(url, body, header, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string().toString();

                if (response.code() == 200) {

                    switch (address) {
                        case HttpUtil.URL_SSO:
                            getLogin(data);
                            break;
                        case HttpUtil.URL_PERMISSION:
                            getPermission(data);
                            break;
                        default:
                            break;
                    }

                }
            }
        });
    }

    private void getLogin(String data) {
        try {
            Login login = GsonUtil.parseJson(data, Login.class);
            if (listener != null) listener.OnLogin(login);
            L.d("sso_login", login.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void getPermission(String data) {
        L.d("Permission", data);
    }


    @Override
    public void setLisenter(OnReceiveLisenter lisenter) {
        this.listener = lisenter;
    }

    public interface OnReceiveLisenter {
        void OnGetConfig(HomeConfig config);

        void OnLogin(Login login);

        void Onfinish();
    }
}
