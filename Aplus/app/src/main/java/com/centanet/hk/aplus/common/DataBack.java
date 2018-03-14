package com.centanet.hk.aplus.common;

/**
 * Created by yangzm4 on 2018/1/26.
 */

public class DataBack<T> {
    private T data;

    public DataBack(T data) {
        this.data = data;
    }

    public DataBack() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
