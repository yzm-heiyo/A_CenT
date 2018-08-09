package com.centanet.hk.aplus.Views.FollowAddView.present;

import com.centanet.hk.aplus.Views.FollowAddView.model.FollowAddModel;
import com.centanet.hk.aplus.Views.FollowAddView.model.IFollowAddModel;
import com.centanet.hk.aplus.Views.FollowAddView.view.IFollowAddView;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public class FollowAddPresent implements IFollowAddPresent {

    IFollowAddView feedBackActivity;
    IFollowAddModel feedBackModel;

    public FollowAddPresent(IFollowAddView feedBackActivity) {
        feedBackModel = FollowAddModel.getInstance();
        this.feedBackActivity = feedBackActivity;
        feedBackModel.setListener(listener);
    }

    FollowAddModel.OnReceiveListener listener = new FollowAddModel.OnReceiveListener() {
        @Override
        public void onReceive(DetailAddressResponse address) {
            feedBackActivity.reFreshAddress(address);
        }
    };

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {
        feedBackModel.doPost(address,headers,bodys);
    }
}
