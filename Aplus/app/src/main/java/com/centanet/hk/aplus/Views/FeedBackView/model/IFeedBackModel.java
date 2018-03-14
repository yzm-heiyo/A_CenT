package com.centanet.hk.aplus.Views.FeedBackView.model;

import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public interface IFeedBackModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);
    void setListener(FeedBackModel.OnReceiveListener listener);
}
