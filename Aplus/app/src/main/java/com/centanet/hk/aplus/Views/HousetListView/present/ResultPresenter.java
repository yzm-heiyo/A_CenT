package com.centanet.hk.aplus.Views.HousetListView.present;

import com.centanet.hk.aplus.Views.HousetListView.model.IResultModel;
import com.centanet.hk.aplus.Views.HousetListView.model.ResultModel;
import com.centanet.hk.aplus.Views.HousetListView.view.IResultFragment;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.house.Properties;

import java.util.List;

/**
 * Created by yangzm4 on 2018/1/31.
 */

public class ResultPresenter implements IResultPresenter {

    private String thiz = getClass().getSimpleName();
    private IResultModel resultModel;
    private IResultFragment resultFragment;
    private ResultModel.OnReceiveListener receiveListener = new ResultModel.OnReceiveListener() {
        @Override
        public void onReceive(List<Properties> dataBack) {
            if (dataBack != null)
                refreshData(dataBack);
        }

        @Override
        public void onReceivelFinish() {
            refreshListView();
        }
    };

    public ResultPresenter(IResultFragment resultFragment) {
        resultModel = ResultModel.getInstance();
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
