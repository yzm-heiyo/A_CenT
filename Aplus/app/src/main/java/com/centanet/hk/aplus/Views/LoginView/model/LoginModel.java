package com.centanet.hk.aplus.Views.LoginView.model;

import android.os.Build;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.entity.login.HomeConfig;
import com.centanet.hk.aplus.entity.login.Login;
import com.centanet.hk.aplus.entity.login.UserPermission;

import java.io.IOException;
import java.util.UUID;

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

    //获得独一无二的Psuedo ID
    @Override
    public String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    @Override
    public void doPost(final String address, Object header, Object body) {
//        String url = address;
//        if (address != HttpUtil.URL_SSO && address != HttpUtil.URL_HomeConfig)
//            url = HttpUtil.URL + address;
        HttpUtil.doPost(address, body, header, new Callback() {
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

        try {
            UserPermission permission = GsonUtil.parseJson(data, UserPermission.class);
            L.d("permission", permission.toString());
            if (listener != null) listener.OnPermissionFinish(permission);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setLisenter(OnReceiveLisenter lisenter) {
        this.listener = lisenter;
    }

    public interface OnReceiveLisenter {

        void OnPermissionFinish(UserPermission permission);

        void OnGetConfig(HomeConfig config);

        void OnLogin(Login login);

        void Onfinish();
    }
}
