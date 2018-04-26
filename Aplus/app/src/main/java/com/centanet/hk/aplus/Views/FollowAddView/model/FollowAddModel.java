package com.centanet.hk.aplus.Views.FollowAddView.model;

import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.detail.DetailAddFollowResponse;
import com.centanet.hk.aplus.bean.detail.DetailAddress;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.eventbus.BaseClass;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_FOLLOW_SUCCESS;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public class FollowAddModel extends BaseClass implements IFollowAddModel {

    private static FollowAddModel followAddModel = new FollowAddModel();
    private OnReceiveListener listener;

    public static synchronized FollowAddModel getInstance() {
        return followAddModel;
    }

    @Override
    public void doPost(final String address, AHeaderDescription headers, Object bodys) {
        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String net = response.body().string().toString();
                switch (address) {
                    case HttpUtil.URL_ADDRESS_DETAIL:
                        parseNetData(net);
                        break;
                    case HttpUtil.URL_FOLLOW_ADD:
                        getAddFollowStatus(net);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void getAddFollowStatus(String net) {
        try {
            DetailAddFollowResponse response =GsonUtil.parseJson(net, DetailAddFollowResponse.class);
            if(response.isFlag())notifyEmptyBusMessage(DETAIL_FOLLOW_SUCCESS);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void parseNetData(String data) {
        if (listener != null) try {
            listener.onReceive(GsonUtil.parseJson(data, DetailAddress.class));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void setListener(OnReceiveListener listener) {
        this.listener = listener;
    }

    public interface OnReceiveListener {
        void onReceive(DetailAddress address);
    }
}
