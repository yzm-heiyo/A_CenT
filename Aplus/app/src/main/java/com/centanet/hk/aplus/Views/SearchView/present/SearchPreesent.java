package com.centanet.hk.aplus.Views.SearchView.present;

import com.centanet.hk.aplus.Views.SearchView.model.ISearchModel;
import com.centanet.hk.aplus.Views.SearchView.model.SearchModel;
import com.centanet.hk.aplus.Views.SearchView.view.ISearchView;
import com.centanet.hk.aplus.entity.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.entity.http.AutoSearchDescription;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class SearchPreesent implements ISearchPresent {

    private ISearchModel searchModel;

    private ISearchView searchView;

    public SearchPreesent(ISearchView searchView) {
        searchModel = SearchModel.getInstance();
        searchModel.setRespontListener(onReceiveListener);
        this.searchView = searchView;
    }

    public void doPost(String urlAutosearch, AHeaderDescription AHeaderDescription, AutoSearchDescription autoSearchDescription){
        searchModel.doPost(urlAutosearch, AHeaderDescription,autoSearchDescription);
    }

    @Override
    public void saveSearchHistory(List<PropertyParamHints> history) {
        searchModel.saveSearchHistory(history);
    }

    @Override
    public void getSearchHistory() {
        searchModel.getSearchHistory(new SearchModel.CallBack() {
            @Override
            public void getData(List<PropertyParamHints> dataBack) {
                searchView.recoverHistoryView(dataBack);
            }
        });
        searchView.refreshHistoryView();
    }

    @Override
    public void recoverLabelHistiry(List<String> history) {
        List<PropertyParamHints> data = searchModel.searchLabelHistory(history);
        searchView.recoverNewHistory(data);
        searchView.recoverHistoryLabelView(searchModel.changeToLabelData(data));
    }

    @Override
    public String changeToLabelData(PropertyParamHints data) {
        return searchModel.changeToLabelData(data);
    }

    private SearchModel.OnReceiveListener onReceiveListener = new SearchModel.OnReceiveListener() {
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
