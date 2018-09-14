package com.centanet.hk.aplus.Views.HouseDetailView.model;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public interface IDetailModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);

    void doGet(String address, AHeaderDescription headers, Object bodys);

    void setOnPropertDetailReceiveListener(DetailModel.OnReceiveListener onReceiveListener);

    void setOnPropertOtherReceiveListener(DetailModel.OnReceiveListener onReceiveListener);

    void setOnPropertNextReceiveListener(DetailModel.OnReceiveListener onReceiveListener);

    void clearNetFlag();

    void getPropertyDetail(int index);

    void getPropertDetailOther(int index);

    String getPropertyKey(int index);

}
