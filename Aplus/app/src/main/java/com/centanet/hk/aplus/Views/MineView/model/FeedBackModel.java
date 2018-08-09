package com.centanet.hk.aplus.Views.MineView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.mine.Infomation;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public class FeedBackModel implements IFeedBackModel {

    private static FeedBackModel feedBackModel = new FeedBackModel();
    private OnReceiveLisenter listener;

    public static synchronized FeedBackModel getInstance() {
        return feedBackModel;
    }


    @Override
    public void doPost(String address, AHeaderDescription header, Object body) {

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        header.setNumber(number);
        header.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + header.getStaffno()));
        HttpUtil.doPost(address, body, header, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().toString();
                L.d("FeedBack", dataBack);
                if (response.code() == 200) {
                    Infomation infomation = null;
                    try {
                        infomation = GsonUtil.parseJson(dataBack, Infomation.class);
                        if (listener != null) listener.OnReceive(infomation);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void setLisenter(OnReceiveLisenter lisenter) {
        this.listener = lisenter;
    }

    public interface OnReceiveLisenter {

        void OnReceive(Infomation infomation);

        void Onfinish();
    }

}
