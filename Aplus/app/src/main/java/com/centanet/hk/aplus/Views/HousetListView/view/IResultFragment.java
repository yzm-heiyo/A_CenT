package com.centanet.hk.aplus.Views.HousetListView.view;

import com.centanet.hk.aplus.entity.house.Properties;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public interface IResultFragment {

    void refreshListView();
    void refreshListData(List<Properties> properties);
    void openFreshView();
    void openLoadView();
}
