package com.centanet.hk.aplus.bean.translist;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/8/7.
 */

public class TransListResponse extends BaseResponse {

    private int Count;
    private List<Transaction> Transactions;
    private String PermisstionsModel;

    public TransListResponse() {
    }

    public TransListResponse(int count, List<Transaction> transactions, String permisstionsModel) {
        Count = count;
        Transactions = transactions;
        PermisstionsModel = permisstionsModel;
    }

    public TransListResponse(boolean flag, String errorMsg, String runTime, int count, List<Transaction> transactions, String permisstionsModel) {
        super(flag, errorMsg, runTime);
        Count = count;
        Transactions = transactions;
        PermisstionsModel = permisstionsModel;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setTransactions(List<Transaction> transactions) {
        Transactions = transactions;
    }

    public void setPermisstionsModel(String permisstionsModel) {
        PermisstionsModel = permisstionsModel;
    }

    public int getCount() {
        return Count;
    }

    public List<Transaction> getTransactions() {
        return Transactions;
    }

    public String getPermisstionsModel() {
        return PermisstionsModel;
    }

    @Override
    public String toString() {
        return "TransListResponse{" +
                "Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                ", Count=" + Count +
                ", Transactions=" + Transactions +
                ", PermisstionsModel='" + PermisstionsModel + '\'' +
                '}';
    }
}
