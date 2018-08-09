package com.centanet.hk.aplus.Views.TransListFragment.view;

import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.translist.Transaction;

import java.util.List;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public interface ITransListView {

    void refreshListData(List<Transaction> properties);
    void refreshTransCount(int count);
    void onFailure();

}
