package com.centanet.hk.aplus.Views.FavoriteView.view;

import com.centanet.hk.aplus.entity.house.Properties;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public interface IFavorieFragment {

    void refreshListView();
    void refreshListData(List<Properties> properties);
    void openFreshView();
    void openLoadView();
    void toLogin();
    void showNoPermissionDialog();
}
