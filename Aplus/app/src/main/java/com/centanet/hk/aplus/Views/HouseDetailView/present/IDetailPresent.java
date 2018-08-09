package com.centanet.hk.aplus.Views.HouseDetailView.present;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public interface IDetailPresent {

    void doPost(String address, AHeaderDescription headers, Object bodys);

    void doGet(String address, AHeaderDescription headers, Object bodys);

    void clearNetFlag();

    void onDestroy();

    void getPropertyDetail(int index);

    String getPropertyKey(int index);
}
