package com.centanet.hk.aplus.Views.FollowView.model;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.DetailFollowResponse;
import com.centanet.hk.aplus.bean.favo.FavoResponse;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/7/11.
 */

public class FollowModel implements IFollowModel {

    private OnReceiveListener addReLisenter;
    private OnReceiveListener favoReLisenter;
    private OnReceiveListener cancelFavoReLisenter;
    private OnReceiveListener followsReLisenter;
    private OnLoadEndLisenter onLoadEndLisenter;
    private static FollowModel followModel = new FollowModel();
    private String thiz = getClass().getSimpleName();
//    private boolean isFollowEnd;


    public static synchronized FollowModel getInstance() {
        return followModel;
    }

    @Override
    public void doPost(String address, AHeaderDescription headers, Object bodys) {

        HttpUtil.doPost(address, bodys, headers, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().trim().toString();
                L.d(thiz, "Response: " + dataBack);
                switch (address) {
                    case HttpUtil.URL_ADDRESS_DETAIL:
                        getAddressDetail(dataBack);
                        break;
                    case HttpUtil.URL_FOLLOWS:
                        getFollow(dataBack);
                        break;
                    case HttpUtil.URL_FAVORITE:
                        if (favoReLisenter != null)
                            favoReLisenter.onReceiveSuccess(parseFavo(dataBack));
                        break;
                    case HttpUtil.URL_CANCELFAVO:
                        if (cancelFavoReLisenter != null)
                            cancelFavoReLisenter.onReceiveSuccess(parseFavo(dataBack));
                        break;
                }
            }
        });
    }

    private boolean parseFavo(String dataBack) {
        try {
            FavoResponse response1 = GsonUtil.parseJson(dataBack, FavoResponse.class);
            return response1.isFlag();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }

    //获取跟进
    private void getFollow(String dataBack) {
        //todo 添加权限验证，以及是否以及到尾判断
        DetailFollowResponse follows = null;
        try {
            follows = GsonUtil.parseJson(dataBack, DetailFollowResponse.class);
            L.d(thiz, follows.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (follows.getPropertyFollows() != null && follows.getPropertyFollows().size() < 5) {
            if (onLoadEndLisenter != null) onLoadEndLisenter.isEnd();
        }

        if (followsReLisenter != null)
            followsReLisenter.onReceiveSuccess(follows.getPropertyFollows());

    }

    private void getAddressDetail(String dataBack) {
        DetailAddressResponse address = null;
        try {
            address = GsonUtil.parseJson(dataBack, DetailAddressResponse.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (addReLisenter != null) addReLisenter.onReceiveSuccess(address);
    }


    @Override
    public void setOnPropertAddressReceiveListener(OnReceiveListener onReceiveListener) {
        addReLisenter = onReceiveListener;
    }

    @Override
    public void setOnPropertFavoReceiveListener(OnReceiveListener onReceiveListener) {
        favoReLisenter = onReceiveListener;
    }

    @Override
    public void setOnPropertCancelFavoReceiveListener(OnReceiveListener onReceiveListener) {
        L.d(thiz,"setOnPropertCancelFavoReceiveListener");
        cancelFavoReLisenter = onReceiveListener;
    }

    @Override
    public void setOnPropertyFollowReceiveListener(OnReceiveListener onReceiveListener) {
        followsReLisenter = onReceiveListener;
    }

    //监听数据是否以及加载到尾
    @Override
    public void setFollowLoadLoadEndLisenter(OnLoadEndLisenter loadEndLisenter) {
        onLoadEndLisenter = loadEndLisenter;
    }

}
