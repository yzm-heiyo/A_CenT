package com.centanet.hk.aplus.Views.SearchView.present;

import com.centanet.hk.aplus.entity.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.entity.http.AutoSearchDescription;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public interface ISearchPresent {

    void doPost(String urlAutosearch, AHeaderDescription AHeaderDescription, AutoSearchDescription autoSearchDescription);
    void saveSearchHistory(List<PropertyParamHints> history);
    void getSearchHistory();
    void recoverLabelHistiry(List<String> history);
    String changeToLabelData(PropertyParamHints data);
}
