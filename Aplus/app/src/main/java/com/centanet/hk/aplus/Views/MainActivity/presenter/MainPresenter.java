package com.centanet.hk.aplus.Views.MainActivity.presenter;

import com.centanet.hk.aplus.Views.MainActivity.view.IMainView;
import com.centanet.hk.aplus.common_model.model.DistrictModel;
import com.centanet.hk.aplus.common_model.model.IDistrictModel;

/**
 * Created by yangzm4 on 2018/7/20.
 */

public class MainPresenter implements IMainPresenter {

    private IDistrictModel districtModel;
    private IMainView mainView;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        districtModel = DistrictModel.getInstance();
    }

    @Override
    public void getDistrict() {
        districtModel.getDistrict();
    }

}
