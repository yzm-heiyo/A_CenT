package com.centanet.hk.aplus.Views.FollowView.presenter;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.FollowView.model.FollowModel;
import com.centanet.hk.aplus.Views.FollowView.model.IFollowModel;
import com.centanet.hk.aplus.Views.FollowView.view.IFollowView;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/11.
 */

public class FollowPresenter implements IFollowPresenter {

    private IFollowView followView;
    private IFollowModel followModel;
    private Reference<IFollowView> mViewRef;
    private String thiz = getClass().getSimpleName();

    public FollowPresenter(IFollowView followView) {
//        this.followView = followView;
        followModel = FollowModel.getInstance();
        this.mViewRef = new WeakReference<IFollowView>(followView);
        this.followView = mViewRef.get();
    }

    @Override
    public void doAddRequest(String address, AHeaderDescription headers, Object bodys) {
        followModel.setOnPropertAddressReceiveListener(new IFollowModel.OnReceiveListener<DetailAddressResponse>() {
            @Override
            public void onReceiveSuccess(DetailAddressResponse dataBack) {
                followView.reFreshAddTxt(dataBack);
            }

            @Override
            public void onReceivelFailure() {

            }
        });
        followModel.doPost(address, headers, bodys);
    }

    @Override
    public void doFollowRequest(String address, AHeaderDescription headers, Object bodys) {
        followModel.setOnPropertyFollowReceiveListener(new IFollowModel.OnReceiveListener<List<PropertyFollow>>() {
            @Override
            public void onReceiveSuccess(List<PropertyFollow> dataBack) {
                followView.reFreshFollow(dataBack);
            }

            @Override
            public void onReceivelFailure() {

            }
        });
        followModel.doPost(address, headers, bodys);
    }

    @Override
    public void doFavoRequest(String address, AHeaderDescription headers, Object bodys) {
        followModel.setOnPropertFavoReceiveListener(new IFollowModel.OnReceiveListener<Boolean>() {
            @Override
            public void onReceiveSuccess(Boolean dataBack) {
                followView.reFreshFavoImg(true);
            }

            @Override
            public void onReceivelFailure() {

            }
        });
        followModel.setFollowLoadLoadEndLisenter(() -> {
        });
        followModel.doPost(address, headers, bodys);
    }

    @Override
    public void doCancelFavoRequest(String address, AHeaderDescription headers, Object bodys) {
        followModel.setOnPropertCancelFavoReceiveListener(new IFollowModel.OnReceiveListener<Boolean>() {
            @Override
            public void onReceiveSuccess(Boolean dataBack) {
                L.d(thiz, "doCancelFavoRequest");
                followView.reFreshFavoImg(false);
            }

            @Override
            public void onReceivelFailure() {

            }
        });
        followModel.doPost(address, headers, bodys);
    }

    @Override
    public void onDestroy() {
        followView = null;
        System.gc();
    }
}
