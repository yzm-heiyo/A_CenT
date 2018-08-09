package com.centanet.hk.aplus.Views.TransListFragment.model;

import com.centanet.hk.aplus.bean.http.AHeaderDescription;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public interface ITransModel {

    void doPost(String address, AHeaderDescription headerDescription, Object body);
    void setTransLisenter(OnReceiveLisenter transLisenter);


    interface OnReceiveLisenter<T> {
        void onFailure();

        void onSuccess(T t);
    }

}
