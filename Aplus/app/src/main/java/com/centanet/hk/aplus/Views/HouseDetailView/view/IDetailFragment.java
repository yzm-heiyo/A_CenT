package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.content.Intent;

import com.centanet.hk.aplus.entity.http.FollowDescription;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public interface IDetailFragment {

    void onRefresh(String address, AHeaderDescription AHeaderDescription, FollowDescription body);

    void trunToActivity(Intent intent);

    void clearFlag();

}
