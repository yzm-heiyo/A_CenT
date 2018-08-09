package com.centanet.hk.aplus.Views.Trans.TransPagerView.presenter;

import com.centanet.hk.aplus.Views.Trans.TransPagerView.model.HomeModel;
import com.centanet.hk.aplus.Views.Trans.TransPagerView.view.IHomeView;
import com.centanet.hk.aplus.Views.basic.OnReceiveListener;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class HomePreaenter implements IHomePresenter {

    private IHomeView homeView;
    private HomeModel homeModel;

    public HomePreaenter(IHomeView homeView) {
        this.homeView = homeView;
        homeModel = HomeModel.getInstance();
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
