package com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.present;

import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.model.ITransSearchModel;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.model.TransSearchModel;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.view.ITransSearchView;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.AutoSearchDescription;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class TransSearchPreesent implements ITransSearchPresent {

    private ITransSearchModel searchModel;

    private ITransSearchView searchView;

    public TransSearchPreesent(ITransSearchView searchView) {
        searchModel = TransSearchModel.getInstance();
        searchModel.setRespontListener(onReceiveListener);
        this.searchView = searchView;
    }

    public void doPost(String urlAutosearch, AHeaderDescription AHeaderDescription, AutoSearchDescription autoSearchDescription) {
        searchModel.doPost(urlAutosearch, AHeaderDescription, autoSearchDescription);
    }

    @Override
    public void saveSearchHistory(List<PropertyParamHints> history) {
        searchModel.saveSearchHistory(history);
    }

    @Override
    public void getSearchHistory() {
        searchModel.getSearchHistory(new TransSearchModel.CallBack() {
            @Override
            public void getData(List<PropertyParamHints> dataBack) {
                searchView.recoverHistoryView(dataBack);
            }
        });
        searchView.refreshHistoryView();
    }

    @Override
    public void recoverLabelHistiry(List<String> history) {
        if (history == null || history.isEmpty()) return;
        List<PropertyParamHints> data = searchModel.searchLabelHistory(history);
        searchView.recoverNewHistory(data);
        searchView.recoverHistoryLabelView(searchModel.changeToLabelData(data));
    }

    @Override
    public void deleteHistory() {
        searchModel.deleteSearchHistory();
    }

    @Override
    public String changeToLabelData(PropertyParamHints data) {
        return searchModel.changeToLabelData(data);
    }

    private TransSearchModel.OnReceiveListener onReceiveListener = new TransSearchModel.OnReceiveListener() {

        @Override
        public void onReceive(List<PropertyParamHints> dataBack) {
            searchView.addListData(dataBack);
        }

        @Override
        public void onReceivelFinish() {
            searchView.refreshHistoryView();
        }
    };
}
