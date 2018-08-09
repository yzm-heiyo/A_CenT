package com.centanet.hk.aplus.bean.http;

/**
 * Created by yangzm4 on 2018/7/16.
 */

public class DetaileNextKeyIdDescription {

    private int PageIndex;    //第几頁
    private int PageSize;     //每页几条
    private int PropertyType; // 列表數據類型 （1：樓盤列表，2：HotList，3：我的樓主，5：我的收藏,6：我的分佣，14：獨家樓盤，15：客戶關注，16：成交記錄，17：租約到期）

    public DetaileNextKeyIdDescription() {
    }

    public DetaileNextKeyIdDescription(int pageIndex, int pageSize, int propertyType) {
        PageIndex = pageIndex;
        PageSize = pageSize;
        PropertyType = propertyType;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setPropertyType(int propertyType) {
        PropertyType = propertyType;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public int getPropertyType() {
        return PropertyType;
    }

    @Override
    public String toString() {
        return "DetaileNextKeyIdDescription{" +
                "PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", PropertyType=" + PropertyType +
                '}';
    }
}
