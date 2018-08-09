package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/7/12.
 */

public class TransListDescription {

    private int PageIndex;
    private int PageSize = 10;
    private boolean Ascending;
    private String SortPropertyName;
    private String KeyId;
    private boolean IsMobileRequest = true;

    public TransListDescription() {
    }

    public TransListDescription(int pageIndex, int pageSize, boolean ascending, String sortPropertyName, String keyId, boolean isMobileRequest) {
        PageIndex = pageIndex;
        PageSize = pageSize;
        Ascending = ascending;
        SortPropertyName = sortPropertyName;
        KeyId = keyId;
        IsMobileRequest = isMobileRequest;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public boolean isAscending() {
        return Ascending;
    }

    public String getSortPropertyName() {
        return SortPropertyName;
    }

    public String getKeyId() {
        return KeyId;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setAscending(boolean ascending) {
        Ascending = ascending;
    }

    public void setSortPropertyName(String sortPropertyName) {
        SortPropertyName = sortPropertyName;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    @Override
    public String toString() {
        return "TransListDescription{" +
                "PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", Ascending=" + Ascending +
                ", SortPropertyName='" + SortPropertyName + '\'' +
                ", KeyId='" + KeyId + '\'' +
                ", IsMobileRequest=" + IsMobileRequest +
                '}';
    }
}
