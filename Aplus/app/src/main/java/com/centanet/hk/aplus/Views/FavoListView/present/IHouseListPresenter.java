package com.centanet.hk.aplus.Views.FavoListView.present;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/7.
 */

public interface IHouseListPresenter {


    void doPost(String address, AHeaderDescription headers, Object bodys);
    void clearFlag();
}
