package com.centanet.hk.aplus.Views.SearchView.model;

import com.centanet.hk.aplus.entity.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public interface ISearchModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setRespontListener(SearchModel.OnReceiveListener receiveListener);
    void saveSearchHistory(List<PropertyParamHints> history);
    void getSearchHistory(SearchModel.CallBack callBack);
    String changeToLabelData(PropertyParamHints data);
    List<String> changeToLabelData(List<PropertyParamHints> datas);
    List<PropertyParamHints> searchLabelHistory(List<String> params);
}
