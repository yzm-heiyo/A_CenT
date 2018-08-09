package com.centanet.hk.aplus.Views.HomePageView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.FollowView.model.FollowModel;
import com.centanet.hk.aplus.Views.basic.OnReceiveListener;
import com.centanet.hk.aplus.bean.home.FastSearchResponse;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.manager.AppNetViewDataManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class HomeModel implements IHomeModel {


    private static HomeModel homeModel = new HomeModel();

    public static synchronized HomeModel getInstance() {
        return homeModel;
    }

    private OnReceiveListener fastSearchLisenter;

    @Override
    public void doGet(String address, AHeaderDescription header, Object body) {
        String add = HttpUtil.URL + address;
        HttpUtil.doGet(add, header, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String databack = response.body().string().toString();
                L.d("HomeModel",databack);
                if (response.isSuccessful()) {
                    switch (address) {
                        case HttpUtil.URL_FAST_SEARCH:
                            try {
                                FastSearchResponse searchResponse = GsonUtil.parseJson(databack, FastSearchResponse.class);
                                AppNetViewDataManager.setTagList(searchResponse.getPropertyFastSearcherTags());
                                if (fastSearchLisenter != null)
                                    fastSearchLisenter.onReceiveSuccess(searchResponse.isFlag());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void setReceiveLisenter(OnReceiveListener lisenter) {
        fastSearchLisenter = lisenter;
    }


}
