package com.centanet.hk.aplus.Views.TransListFragment.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.translist.TransListResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.Utils.net.GsonUtil.parseJson;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_TAG_TRANLIST;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_TRAN_LIST;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TransModel implements ITransModel {

    private OnReceiveLisenter transListLisenter;
    private static TransModel transModel = new TransModel();

    public static TransModel newInstance() {
        return transModel;
    }


    @Override
    public void doPost(String address, AHeaderDescription headers, Object body) {

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        headers.setNumber(number);
        headers.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + headers.getStaffno()));

        HttpUtil.doPost(address, body, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                transListLisenter.onFailure(404);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    String resp = response.body().string().toString();
                    L.d("TransResp", resp);
                    switch (address) {
                        case URL_TAG_TRANLIST:
                            parseTransList(resp);
                            break;
                    }
                }else transListLisenter.onFailure(500);
            }
        });
    }

    @Override
    public void setTransLisenter(OnReceiveLisenter transLisenter) {
        transListLisenter = transLisenter;
    }

    private void parseTransList(String resp) {

        try {
            TransListResponse transListResponse = GsonUtil.parseJson(resp, TransListResponse.class);
            if (transListLisenter != null) transListLisenter.onSuccess(transListResponse);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
