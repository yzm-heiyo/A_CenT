package com.centanet.hk.aplus.bean.transrecord_list;

import com.centanet.hk.aplus.bean.BaseResponse;
import com.centanet.hk.aplus.bean.detail.PropertyTransaction;
import com.centanet.hk.aplus.bean.login.Permisstions;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/12.
 */

public class TranListResponse extends BaseResponse{

    private int Count;
    private List<PropertyTransaction> Transactions;
    private Permisstions PermisstionsModel;

    public TranListResponse() {
    }

    public TranListResponse(int count, List<PropertyTransaction> transactions, Permisstions permisstionsModel) {
        Count = count;
        Transactions = transactions;
        PermisstionsModel = permisstionsModel;
    }

    public TranListResponse(boolean flag, String errorMsg, String runTime, int count, List<PropertyTransaction> transactions, Permisstions permisstionsModel) {
        super(flag, errorMsg, runTime);
        Count = count;
        Transactions = transactions;
        PermisstionsModel = permisstionsModel;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setTransactions(List<PropertyTransaction> transactions) {
        Transactions = transactions;
    }


    public int getCount() {
        return Count;
    }

    public List<PropertyTransaction> getTransactions() {
        return Transactions;
    }


    @Override
    public String toString() {
        return "TranListResponse{" +
                "Count=" + Count +
                ", Transactions=" + Transactions +
                '}';
    }
}
