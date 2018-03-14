package com.centanet.hk.aplus.Views.HouseDetailView.present;

import com.centanet.hk.aplus.Views.HouseDetailView.model.DetailModel;
import com.centanet.hk.aplus.Views.HouseDetailView.model.IDetailModel;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDetailView;
import com.centanet.hk.aplus.entity.detail.DetailHouse;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailPresent implements IDetailPresent {

    private IDetailModel detailModel;

    private IDetailView detailView;

    public DetailPresent(IDetailView detailView) {
        detailModel = DetailModel.getInstance();
        detailModel.setOnReceiveListener(onReceiveListener);
        this.detailView = detailView;
    }


    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {
        detailModel.doPost(address,headers,bodys);
    }

    @Override
    public void clearNetFlag() {
        detailModel.clearNetFlag();
    }

    private DetailModel.OnReceiveListener onReceiveListener = new DetailModel.OnReceiveListener() {
        @Override
        public void onReceive(DetailHouse dataBack) {
            detailView.refreshListData(dataBack);
        }

        @Override
        public void onReceivelFinish() {

        }
    };

}
