package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;

import org.litepal.crud.LitePalSupport;

/**
 * 收藏请求参数的Manager
 * Created by yangzm4 on 2018/8/2.
 */

public class FavoRequestParamsManager extends LitePalSupport {

    public static PropertyRequestSaveParams params = new PropertyRequestSaveParams();

    public static void setParams(PropertyRequestSaveParams params) {
        FavoRequestParamsManager.params = params;
    }

    public static PropertyRequestSaveParams getParams() {
        return params;
    }
}
