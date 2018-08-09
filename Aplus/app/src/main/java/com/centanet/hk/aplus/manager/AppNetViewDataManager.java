package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.bean.home.PropertyFastSearcherTag;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class AppNetViewDataManager {

    private static List<PropertyFastSearcherTag> tagList;

    public static void setTagList(List<PropertyFastSearcherTag> tagList) {
        AppNetViewDataManager.tagList = tagList;
    }

    public static List<PropertyFastSearcherTag> getTagList() {
        return tagList;
    }
}
