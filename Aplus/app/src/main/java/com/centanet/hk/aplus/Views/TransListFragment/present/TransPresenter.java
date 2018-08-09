package com.centanet.hk.aplus.Views.TransListFragment.present;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.TransListFragment.model.ITransModel;
import com.centanet.hk.aplus.Views.TransListFragment.model.TransModel;
import com.centanet.hk.aplus.Views.TransListFragment.view.ITransListView;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.translist.TransListResponse;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TransPresenter implements ITransPresenter {

    private ITransListView view;
    private ITransModel iTransModel;

    public TransPresenter(ITransListView view) {
        this.view = view;
        iTransModel = TransModel.newInstance();
    }


    @Override
    public void getTransList(String address, AHeaderDescription header, Object body) {
        iTransModel.setTransLisenter(new ITransModel.OnReceiveLisenter<TransListResponse>() {
            @Override
            public void onFailure() {
                view.onFailure();
            }

            @Override
            public void onSuccess(TransListResponse transListResponse) {
                L.d("TransResp", "onSuccess");
                view.refreshTransCount(transListResponse.getCount());
                view.refreshListData(transListResponse.getTransactions());
            }
        });
        iTransModel.doPost(address,header,body);

    }

}
