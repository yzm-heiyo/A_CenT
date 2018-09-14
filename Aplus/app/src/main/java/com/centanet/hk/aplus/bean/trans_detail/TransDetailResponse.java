package com.centanet.hk.aplus.bean.trans_detail;

import com.centanet.hk.aplus.bean.BaseResponse;
import com.centanet.hk.aplus.bean.translist.Transaction;

import java.util.List;

public class TransDetailResponse extends BaseResponse {

    private String DetailAddressChInfo;
    private String DetailAddressEnInfo;
    private String PropertyStatusCode;
    private String DetailAddressEnNoFoolrInfo;
    private String DetailAddressChNoFoolrInfo;
    private boolean UserIsShowDetailFloor;
    private boolean UserIsShowAddressDetail;
    private int Count;
    private List<Transaction> Transactions;
    private String PermisstionsModel;

    public void setDetailAddressChInfo(String detailAddressChInfo) {
        DetailAddressChInfo = detailAddressChInfo;
    }

    public void setDetailAddressEnInfo(String detailAddressEnInfo) {
        DetailAddressEnInfo = detailAddressEnInfo;
    }

    public void setPropertyStatusCode(String propertyStatusCode) {
        PropertyStatusCode = propertyStatusCode;
    }

    public void setDetailAddressEnNoFoolrInfo(String detailAddressEnNoFoolrInfo) {
        DetailAddressEnNoFoolrInfo = detailAddressEnNoFoolrInfo;
    }

    public void setDetailAddressChNoFoolrInfo(String detailAddressChNoFoolrInfo) {
        DetailAddressChNoFoolrInfo = detailAddressChNoFoolrInfo;
    }

    public void setUserIsShowDetailFloor(boolean userIsShowDetailFloor) {
        UserIsShowDetailFloor = userIsShowDetailFloor;
    }

    public void setUserIsShowAddressDetail(boolean userIsShowAddressDetail) {
        UserIsShowAddressDetail = userIsShowAddressDetail;
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

    public String getDetailAddressChInfo() {
        return DetailAddressChInfo;
    }

    public String getDetailAddressEnInfo() {
        return DetailAddressEnInfo;
    }

    public String getPropertyStatusCode() {
        return PropertyStatusCode;
    }

    public String getDetailAddressEnNoFoolrInfo() {
        return DetailAddressEnNoFoolrInfo;
    }

    public String getDetailAddressChNoFoolrInfo() {
        return DetailAddressChNoFoolrInfo;
    }

    public boolean isUserIsShowDetailFloor() {
        return UserIsShowDetailFloor;
    }

    public boolean isUserIsShowAddressDetail() {
        return UserIsShowAddressDetail;
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
}
