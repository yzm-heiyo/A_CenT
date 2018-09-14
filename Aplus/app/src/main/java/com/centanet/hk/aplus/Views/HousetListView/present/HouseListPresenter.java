package com.centanet.hk.aplus.Views.HousetListView.present;

import com.centanet.hk.aplus.Views.HousetListView.model.IHouseListModel;
import com.centanet.hk.aplus.Views.HousetListView.model.HouseListModel;
import com.centanet.hk.aplus.Views.HousetListView.view.IHouseListFragment;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.login.Permisstions;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public class HouseListPresenter implements IHouseListPresenter {

    private String thiz = getClass().getSimpleName();
    private IHouseListModel resultModel;
    private IHouseListFragment resultFragment;


    public HouseListPresenter(IHouseListFragment resultFragment) {
        resultModel = HouseListModel.getInstance();
        resultModel.setRespontListener(receiveListener);
        resultModel.setStatusChangeLisenter(statusChangeLisenter);
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

    @Override
    public boolean isAbleToScreen() {
        return resultModel.isAbleToScreen();
    }

    @Override
    public void setAbleToScreen(boolean b) {
        resultModel.setAbleToScreen(b);
    }

    public void refreshData(List<Properties> properties) {
        resultFragment.refreshListData(properties);
    }

    public void refreshListView() {
        resultFragment.refreshListView();
    }

    private HouseListModel.OnHouseStatusChangeLisenter statusChangeLisenter = new HouseListModel.OnHouseStatusChangeLisenter() {
        @Override
        public void setFavoCancel() {
            resultFragment.setCancelFavo();
        }

        @Override
        public void setFavo() {
            resultFragment.setFavo();
        }
    };
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
        public void onFailure() {
            resultFragment.onFailure();
        }

        @Override
        public void onReceivelFinish() {
            refreshListView();
        }
    };

}
