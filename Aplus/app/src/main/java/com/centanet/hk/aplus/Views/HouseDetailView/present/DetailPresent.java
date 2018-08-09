package com.centanet.hk.aplus.Views.HouseDetailView.present;

import com.centanet.hk.aplus.Views.FollowView.view.IFollowView;
import com.centanet.hk.aplus.Views.HouseDetailView.model.DetailModel;
import com.centanet.hk.aplus.Views.HouseDetailView.model.IDetailModel;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDetailView;
import com.centanet.hk.aplus.bean.detail.DetailBriefInfo;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailPresent implements IDetailPresent {

    private IDetailModel detailModel;

    private IDetailView detailView;

    private Reference<IDetailView> mViewRef;

    public DetailPresent(IDetailView detailView) {
        detailModel = DetailModel.getInstance();
        detailModel.setOnPropertDetailReceiveListener(onProDetailReceiveListener);
        detailModel.setOnPropertOtherReceiveListener(onProOtherReceiveListener);
//        this.detailView = detailView;
        mViewRef = new WeakReference<IDetailView>(detailView);
        this.detailView = mViewRef.get();
    }

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {
        detailModel.doPost(address, headers, bodys);
    }

    @Override
    public void doGet(String address, AHeaderDescription headers, Object bodys) {
        detailModel.doGet(address, headers, bodys);
    }

    @Override
    public void clearNetFlag() {
        detailModel.clearNetFlag();
    }

    @Override
    public void onDestroy() {
        detailView = null;
        System.gc();
    }

    @Override
    public void getPropertyDetail(int index) {
        detailModel.getPropertyDetail(index);
    }

    @Override
    public String getPropertyKey(int index) {
        return detailModel.getPropertyKey(index);
    }

    private DetailModel.OnReceiveListener onProDetailReceiveListener = new DetailModel.OnReceiveListener<DetailHouse>() {
        @Override
        public void onReceive(DetailHouse dataBack) {
            if (detailView != null)
                detailView.refreshListData(dataBack);
        }

        @Override
        public void onReceivelFinish() {

        }
    };

    private DetailModel.OnReceiveListener onProOtherReceiveListener = new DetailModel.OnReceiveListener<DetailBriefInfo>() {
        @Override
        public void onReceive(DetailBriefInfo dataBack) {
            if (detailView != null) detailView.refreshFragment(dataBack);
        }

        @Override
        public void onReceivelFinish() {

        }
    };


}
