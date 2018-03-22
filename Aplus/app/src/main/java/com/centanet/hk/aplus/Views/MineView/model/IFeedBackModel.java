package com.centanet.hk.aplus.Views.MineView.model;

import com.centanet.hk.aplus.Views.LoginView.model.LoginModel;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public interface IFeedBackModel {
    void doPost(String address,Object header,Object body);
    void setLisenter(FeedBackModel.OnReceiveLisenter lisenter);
}
