package com.centanet.hk.aplus.Views.FollowView.presenter;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/7/11.
 */

public interface IFollowPresenter {

    void doAddRequest(String address, AHeaderDescription headers, Object bodys);

    void doFollowRequest(String address, AHeaderDescription headers, Object bodys);

    void doFavoRequest(String address, AHeaderDescription headers, Object bodys);

    void doCancelFavoRequest(String address, AHeaderDescription headers, Object bodys);

    void onDestroy();
}
