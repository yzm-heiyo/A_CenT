package com.centanet.hk.aplus.api;


import android.support.annotation.IntDef;

import com.centanet.hk.aplus.Utils.net.HttpUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.Callback;

/**
 * todo 网络请求框架基类
 * Created by yangzm4 on 2018/6/29.
 */

public abstract class BaseApi {

    public static final int REQUEST_POST = 0;
    public static final int REQUEST_GET = 1;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({REQUEST_GET, REQUEST_POST})
    public @interface RequestType {
    }

    protected abstract String getBaseUrl();

    protected abstract String getPath();

    public void doRequest(@RequestType int requestType, Object body, Object header, Callback callback) {
        switch (requestType) {
            case REQUEST_POST:
                doPost(body, header, callback);
                break;
            case REQUEST_GET:
                doGet(body, header, callback);
                break;
        }
    }

    public void doRequest(@RequestType int requestType, Object body, Object header) {
        switch (requestType) {
            case REQUEST_POST:
                doPost(body, header, null);
                break;
            case REQUEST_GET:
                doGet(body, header, null);
                break;
        }
    }

    private void doPost(Object body, Object header, Callback callback) {
        HttpUtil.doPost(getBaseUrl() + getPath(), body, header, callback);
    }

    private void doGet(Object body, Object header, Callback callback) {
        HttpUtil.doGet(getBaseUrl() + getPath(), header, body, callback);
    }

}
