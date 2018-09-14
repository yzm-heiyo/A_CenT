package com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.model;

import com.centanet.hk.aplus.Views.basic.OnReceiveListener;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public interface ITransHomeModel {

    void doGet(String address, AHeaderDescription header, Object body);
    void setReceiveLisenter(OnReceiveListener lisenter);
}
