package com.centanet.hk.aplus.Views.FollowView.model;

import com.centanet.hk.aplus.Views.HouseDetailView.model.DetailModel;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/7/11.
 */

public interface IFollowModel {

    void doPost(String address, AHeaderDescription headers, Object bodys);

    void setOnPropertAddressReceiveListener(OnReceiveListener onReceiveListener);

    void setOnPropertFavoReceiveListener(OnReceiveListener onReceiveListener);

    void setOnPropertCancelFavoReceiveListener(OnReceiveListener onReceiveListener);

    void setOnPropertyFollowReceiveListener(OnReceiveListener onReceiveListener);

    void setFollowLoadLoadEndLisenter(OnLoadEndLisenter loadEndLisenter);

    interface OnReceiveListener<T> {
        void onReceiveSuccess(T dataBack);
        void onReceivelFailure();
    }

    interface OnLoadEndLisenter {
        void isEnd();
    }

}
