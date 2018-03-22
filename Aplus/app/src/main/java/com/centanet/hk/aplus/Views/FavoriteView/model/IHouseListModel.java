package com.centanet.hk.aplus.Views.FavoriteView.model;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public interface IHouseListModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setRespontListener(HouseListModel.OnReceiveListener receiveListener);
    void clearFlag();
}
