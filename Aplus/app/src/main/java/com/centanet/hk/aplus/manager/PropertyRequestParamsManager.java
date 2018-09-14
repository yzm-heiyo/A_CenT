package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 房源请求参数的Manager
 * Created by yangzm4 on 2018/8/2.
 */

public class PropertyRequestParamsManager extends LitePalSupport {

    public static PropertyRequestSaveParams params = new PropertyRequestSaveParams();

    public static void setParams(PropertyRequestSaveParams params) {
        PropertyRequestParamsManager.params = params;
    }

    public static PropertyRequestSaveParams getParams() {
        return params;
    }
}
