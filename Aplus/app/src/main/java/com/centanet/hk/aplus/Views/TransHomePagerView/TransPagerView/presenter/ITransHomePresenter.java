package com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.presenter;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public interface ITransHomePresenter {

    void doGet(String address, AHeaderDescription header, Object body);

}
