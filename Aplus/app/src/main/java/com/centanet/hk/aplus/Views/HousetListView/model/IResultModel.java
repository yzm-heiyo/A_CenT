package com.centanet.hk.aplus.Views.HousetListView.model;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public interface IResultModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setRespontListener(ResultModel.OnReceiveListener receiveListener);
    void clearFlag();
}
