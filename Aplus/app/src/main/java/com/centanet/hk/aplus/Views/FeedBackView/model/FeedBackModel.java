package com.centanet.hk.aplus.Views.FeedBackView.model;

import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.entity.detail.DetailAddress;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/3/12.
 */

public class FeedBackModel implements IFeedBackModel {

    private static FeedBackModel feedBackModel = new FeedBackModel();
    private OnReceiveListener listener;

    public static synchronized FeedBackModel getInstance() {
        return feedBackModel;
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
                    default:
                        break;
                }
            }
        });
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
