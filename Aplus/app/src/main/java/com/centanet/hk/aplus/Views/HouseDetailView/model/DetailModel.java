package com.centanet.hk.aplus.Views.HouseDetailView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.entity.detail.DetailAddress;
import com.centanet.hk.aplus.entity.detail.DetailFollow;
import com.centanet.hk.aplus.entity.detail.DetailHouse;
import com.centanet.hk.aplus.entity.detail.DetailTrustor;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.eventbus.BaseClass;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_ADDRESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_DETAILDATA;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailModel extends BaseClass implements IDetailModel {

    private String thiz = getClass().getSimpleName();
    boolean isEnd = false;

    private static DetailModel detailModel = new DetailModel();

    private OnReceiveListener onReceiveListener;

    private DetailHouse houseData;

    public static synchronized DetailModel getInstance() {
        return detailModel;
    }

    @Override
    public void doPost(final String address, AHeaderDescription headers, final Object bodys) {


        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().trim().toString();
                L.d(thiz + "-Response", dataBack);
                switch (address) {
                    case HttpUtil.URL_ADDRESS_DETAIL:
                        getAddressDetail(dataBack);
                        break;
                    case HttpUtil.URL_DETAIL:
                        getDeatail(dataBack);
                        break;
                    case HttpUtil.URL_FOLLOWS:
                        if (isEnd) {
                            notifyEmptyBusMessage(DETAIL_FOLLOW);
                            return;
                        }
                        getFollow(dataBack);
                        break;
                    case HttpUtil.URL_TRUSTOR:
                        getTrustor(dataBack);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getTrustor(String dataBack) {
        DetailTrustor detailTrustor;
        try {
            detailTrustor = GsonUtil.parseJson(dataBack,DetailTrustor.class);
            L.d(thiz,"permission: "+detailTrustor.isDeptContactInformationSearch()+"");
            L.d(thiz,detailTrustor.getTrustors().toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void getFollow(String dataBack) {
        DetailFollow follows = null;
        try {
            follows = GsonUtil.parseJson(dataBack, DetailFollow.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (follows.getPropertyFollows().size() < 5) isEnd = true;
        delayNoticeBusMessage(DETAIL_FOLLOW, follows.getPropertyFollows());
    }

    private void getAddressDetail(String dataBack) {
        DetailAddress address = null;
        try {
            address = GsonUtil.parseJson(dataBack, DetailAddress.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        notifyBusMessage(DETAIL_ADDRESS, address.getDetailAddressChInfo());
    }

    private void getDeatail(String dataBack) {

        try {
            houseData = GsonUtil.parseJson(dataBack, DetailHouse.class);
            if (onReceiveListener != null) onReceiveListener.onReceive(houseData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        delayNoticeBusMessage(DETAIL_DETAILDATA, houseData);

    }

    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    @Override
    public void clearNetFlag() {
        isEnd = false;
    }

    public interface OnReceiveListener {
        void onReceive(DetailHouse dataBack);

        void onReceivelFinish();
    }
}
