package com.centanet.hk.aplus.eventbus;

/**
 * Created by yangzm4 on 2018/2/1.
 */

public class MsgData<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
