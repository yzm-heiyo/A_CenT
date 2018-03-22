package com.centanet.hk.aplus.Views.HousetListView.present;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/7.
 */

public interface IHouseListPresenter {


    void doPost(String address, AHeaderDescription headers, Object bodys);
    void clearFlag();
}
