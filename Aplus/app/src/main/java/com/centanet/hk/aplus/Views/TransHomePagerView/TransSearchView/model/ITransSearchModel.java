package com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.model;

import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public interface ITransSearchModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setRespontListener(TransSearchModel.OnReceiveListener receiveListener);
    void saveSearchHistory(List<PropertyParamHints> history);
    void getSearchHistory(TransSearchModel.CallBack callBack);
    void deleteSearchHistory();
    String changeToLabelData(PropertyParamHints data);
    List<String> changeToLabelData(List<PropertyParamHints> datas);
    List<PropertyParamHints> searchLabelHistory(List<String> params);
}
