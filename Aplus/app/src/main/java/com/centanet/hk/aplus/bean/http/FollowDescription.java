package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/3/9.
 */

public class FollowDescription {

    private String PropertyKeyId;
    private String FollowTypeKeyId;
    private String FollowTimeFrom;
    private String FollowTimeTo;
    private String Keyword = "";
    private boolean IsDetails = true;
    private String FollowPersonKeyId;
    private String FollowDeptKeyId;
    private int PageIndex;
    private String SortField = "";
    private int PageSize = 5;
    private boolean Ascending = false;
    private boolean IsMobileRequest = true;

    public void setPropertyKeyId(String propertyKeyId) {
        PropertyKeyId = propertyKeyId;
    }

    public void setFollowTypeKeyId(String followTypeKeyId) {
        FollowTypeKeyId = followTypeKeyId;
    }

    public void setFollowTimeFrom(String followTimeFrom) {
        FollowTimeFrom = followTimeFrom;
    }

    public void setFollowTimeTo(String followTimeTo) {
        FollowTimeTo = followTimeTo;
    }

    public void setKeyword(String keyword) {
        Keyword = keyword;
    }

    public void setDetails(boolean details) {
        IsDetails = details;
    }

    public void setFollowPersonKeyId(String followPersonKeyId) {
        FollowPersonKeyId = followPersonKeyId;
    }

    public void setFollowDeptKeyId(String followDeptKeyId) {
        FollowDeptKeyId = followDeptKeyId;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public void setSortField(String sortField) {
        SortField = sortField;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setAscending(boolean ascending) {
        Ascending = ascending;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public String getPropertyKeyId() {
        return PropertyKeyId;
    }

    public String getFollowTypeKeyId() {
        return FollowTypeKeyId;
    }

    public String getFollowTimeFrom() {
        return FollowTimeFrom;
    }

    public String getFollowTimeTo() {
        return FollowTimeTo;
    }

    public String getKeyword() {
        return Keyword;
    }

    public boolean isDetails() {
        return IsDetails;
    }

    public String getFollowPersonKeyId() {
        return FollowPersonKeyId;
    }

    public String getFollowDeptKeyId() {
        return FollowDeptKeyId;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public String getSortField() {
        return SortField;
    }

    public int getPageSize() {
        return PageSize;
    }

    public boolean isAscending() {
        return Ascending;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }
}
