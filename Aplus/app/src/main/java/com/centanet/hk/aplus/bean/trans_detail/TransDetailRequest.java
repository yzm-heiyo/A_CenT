package com.centanet.hk.aplus.bean.trans_detail;

public class TransDetailRequest {

    private int PageIndex = 1;
    private int PageSize = 15;
    private boolean Ascending;
    private String SortPropertyName;
    private String KeyId;
    private boolean IsMobileRequest = true;

    public TransDetailRequest() {
    }

    public TransDetailRequest(int pageIndex, int pageSize, boolean ascending, String sortPropertyName, String keyId, boolean isMobileRequest) {
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
}
