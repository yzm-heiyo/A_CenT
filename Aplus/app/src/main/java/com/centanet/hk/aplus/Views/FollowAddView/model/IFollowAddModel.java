package com.centanet.hk.aplus.Views.FollowAddView.model;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public interface IFollowAddModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setListener(FollowAddModel.OnReceiveListener listener);
}
