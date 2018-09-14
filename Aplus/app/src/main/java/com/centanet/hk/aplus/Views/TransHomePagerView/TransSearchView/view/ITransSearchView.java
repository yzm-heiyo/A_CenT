package com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.view;

import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public interface ITransSearchView {

    void addListData(List<PropertyParamHints> data);
    void recoverHistoryView(List<PropertyParamHints> history);
    void recoverHistoryLabelView(List<String> history);
    void recoverNewHistory(List<PropertyParamHints> history);
    void refreshHistoryView();
}
