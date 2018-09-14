package com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.presenter;

import com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.model.TransHomeModel;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.view.IHomeView;
import com.centanet.hk.aplus.Views.basic.OnReceiveListener;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class TransHomePreaenter implements ITransHomePresenter {

    private IHomeView homeView;
    private TransHomeModel homeModel;

    public TransHomePreaenter(IHomeView homeView) {
        this.homeView = homeView;
        homeModel = TransHomeModel.getInstance();
    }

    @Override
    public void doGet(String address, AHeaderDescription header, Object body) {
        homeModel.setReceiveLisenter(new OnReceiveListener<Boolean>() {
            @Override
            public void onReceiveSuccess(Boolean dataBack) {
                if(dataBack == true)homeView.regfreshFastTag();
            }

            @Override
            public void onReceivelFailure() {

            }
        });
        homeModel.doGet(address, header, body);
    }

}
