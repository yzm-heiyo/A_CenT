package com.centanet.hk.aplus.Views.HousetListView.model;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public interface IHouseListModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setRespontListener(HouseListModel.OnReceiveListener receiveListener);
    void clearFlag();
    void setStatusChangeLisenter(HouseListModel.OnHouseStatusChangeLisenter statusChangeLisenter);
}
