package com.centanet.hk.aplus.Views.basic;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public interface OnReceiveListener<T> {
        void onReceiveSuccess(T dataBack);
        void onReceivelFailure();
}
