package com.centanet.hk.aplus.Views.FavoriteView.present;

import com.centanet.hk.aplus.Views.FavoriteView.model.HouseListModel;
import com.centanet.hk.aplus.Views.FavoriteView.model.IHouseListModel;
import com.centanet.hk.aplus.Views.FavoriteView.view.IFavorieFragment;
import com.centanet.hk.aplus.entity.house.Properties;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.login.Permisstions;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public class HouseListPresenter implements IHouseListPresenter {

    private String thiz = getClass().getSimpleName();
    private IHouseListModel resultModel;
    private IFavorieFragment resultFragment;
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

    public HouseListPresenter(IFavorieFragment resultFragment) {
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
