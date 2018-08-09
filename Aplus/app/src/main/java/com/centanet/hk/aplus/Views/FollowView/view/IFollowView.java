package com.centanet.hk.aplus.Views.FollowView.view;

import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/11.
 */

public interface IFollowView {

    void reFreshFollow(List<PropertyFollow> follows);

    void reFreshFavoImg(boolean favo);

    void reFreshAddTxt(DetailAddressResponse address);

    void toastFollowsEnd();

}
