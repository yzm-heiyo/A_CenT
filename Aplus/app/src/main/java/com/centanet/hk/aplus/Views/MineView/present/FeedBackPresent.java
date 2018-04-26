package com.centanet.hk.aplus.Views.MineView.present;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.MineView.model.FeedBackModel;
import com.centanet.hk.aplus.Views.MineView.model.IFeedBackModel;
import com.centanet.hk.aplus.Views.MineView.view.IMineView;
import com.centanet.hk.aplus.bean.mine.Infomation;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public class FeedBackPresent implements IFeedBackPresent {

    private IMineView mineView;
    private IFeedBackModel feedBackModel;

    public FeedBackPresent(IMineView mineView) {
        feedBackModel = FeedBackModel.getInstance();
        feedBackModel.setLisenter(listener);
        this.mineView = mineView;
    }

    FeedBackModel.OnReceiveLisenter listener = new FeedBackModel.OnReceiveLisenter() {
        @Override
        public void OnReceive(Infomation infomation) {
            if (infomation != null)
                mineView.refreshView(infomation);
        }

        @Override
        public void Onfinish() {

        }
    };


    @Override
    public void doPose(String address, Object header, Object body) {
        feedBackModel.doPost(address, header, body);
    }
}
