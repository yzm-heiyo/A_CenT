package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.save_option.TransRequestSaveParams;

import org.litepal.crud.LitePalSupport;

/**
 * 成交请求参数的Manager
 * Created by yangzm4 on 2018/8/2.
 */

public class TransRequestParamsManager extends LitePalSupport {

    public static TransRequestSaveParams params = new TransRequestSaveParams();

    public static void setParams(TransRequestSaveParams params) {
        TransRequestParamsManager.params = params;
    }

    public static TransRequestSaveParams getParams() {
        return params;
    }
}
