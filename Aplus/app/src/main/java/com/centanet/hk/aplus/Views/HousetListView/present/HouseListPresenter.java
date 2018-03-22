package com.centanet.hk.aplus.Views.HousetListView.present;

import com.centanet.hk.aplus.Views.HousetListView.model.IHouseListModel;
import com.centanet.hk.aplus.Views.HousetListView.model.HouseListModel;
import com.centanet.hk.aplus.Views.HousetListView.view.IHouseListFragment;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.house.Properties;
import com.centanet.hk.aplus.entity.login.Permisstions;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public class HouseListPresenter implements IHouseListPresenter {

    private String thiz = getClass().getSimpleName();
    private IHouseListModel resultModel;
    private IHouseListFragment resultFragment;
    private HouseListModel.OnReceiveListener receiveListener = new HouseListModel.OnReceiveListener() {
        @Override
        public void onReceiveHouseData(List<Properties> dataBack) {
            if (dataBack != null)
                refreshData(dataBack);
        }

        @Override
        public void onReceivePermission(Permisstions permisstions) {
            resultFragment.toLogin();
        }

        @Override
        public void onReceivelFinish() {
            refreshListView();
        }
    };

    public HouseListPresenter(IHouseListFragment resultFragment) {
        resultModel = HouseListModel.getInstance();
        resultModel.setRespontListener(receiveListener);
        this.resultFragment = resultFragment;
    }

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {
        resultModel.doPost(address, headers, bodys);
    }

    @Override
    public void clearFlag() {
        resultModel.clearFlag();
    }

    public void refreshData(List<Properties> properties) {
        resultFragment.refreshListData(properties);
    }

    public void refreshListView() {
        resultFragment.refreshListView();
    }


}
