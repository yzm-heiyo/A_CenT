package com.centanet.hk.aplus.Views.TransListFragment.present;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public interface ITransPresenter {

    void getTransList(String address, AHeaderDescription header, Object body);

}
