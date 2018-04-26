package com.centanet.hk.aplus.Views.HouseFragment.view;

import com.centanet.hk.aplus.bean.house.Properties;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public interface IHouseListFragment {

    void refreshListView();
    void refreshListData(List<Properties> properties);
    void openFreshView();
    void openLoadView();
    void toLogin();
    void showNoPermissionDialog();
    void onFailure();
    void setCancelFavo();
    void setFavo();
}
