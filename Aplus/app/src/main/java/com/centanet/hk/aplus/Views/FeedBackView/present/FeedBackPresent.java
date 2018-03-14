package com.centanet.hk.aplus.Views.FeedBackView.present;

import com.centanet.hk.aplus.Views.FeedBackView.model.FeedBackModel;
import com.centanet.hk.aplus.Views.FeedBackView.model.IFeedBackModel;
import com.centanet.hk.aplus.Views.FeedBackView.view.IFeedBackView;
import com.centanet.hk.aplus.entity.detail.DetailAddress;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public class FeedBackPresent implements IFeedBackPresent{

    IFeedBackView feedBackActivity;
    IFeedBackModel feedBackModel;

    public FeedBackPresent(IFeedBackView feedBackActivity) {
        feedBackModel = FeedBackModel.getInstance();
        this.feedBackActivity = feedBackActivity;
        feedBackModel.setListener(listener);
    }

    FeedBackModel.OnReceiveListener listener = new FeedBackModel.OnReceiveListener() {
        @Override
        public void onReceive(DetailAddress address) {
            feedBackActivity.reFreshAddress(address.getDetailAddressChInfo());
        }
    };

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {
        feedBackModel.doPost(address,headers,bodys);
    }
}
