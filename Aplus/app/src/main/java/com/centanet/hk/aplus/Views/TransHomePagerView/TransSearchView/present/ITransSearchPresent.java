package com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.present;

import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.AutoSearchDescription;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public interface ITransSearchPresent {

    void doPost(String urlAutosearch, AHeaderDescription AHeaderDescription, AutoSearchDescription autoSearchDescription);
    void saveSearchHistory(List<PropertyParamHints> history);
    void getSearchHistory();
    void recoverLabelHistiry(List<String> history);
    void deleteHistory();
    String changeToLabelData(PropertyParamHints data);
}
