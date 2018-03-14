package com.centanet.hk.aplus.Views.HouseDetailView.model;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public interface IDetailModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setOnReceiveListener(DetailModel.OnReceiveListener onReceiveListener);
    void clearNetFlag();
}
