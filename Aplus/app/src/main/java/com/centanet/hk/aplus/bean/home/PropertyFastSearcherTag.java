package com.centanet.hk.aplus.bean.home;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class PropertyFastSearcherTag implements Serializable{

    private int Seq;
    private int PropertyType;//（1：樓盤列表，2：HotList，3：我的樓主，5：我的收藏,6：我的分佣，14：獨家樓盤，15：客戶關注，16：成交記錄，17：租約到期）
    private boolean IsDelete;
    private String ItemImgUrl;
    private String ItemValue;
    private String ItemText;
    private String KeyId;

    public PropertyFastSearcherTag() {
    }

    public PropertyFastSearcherTag(int seq, int propertyType, boolean isDelete, String itemImgUrl, String itemValue, String itemText, String keyId) {
        Seq = seq;
        PropertyType = propertyType;
        IsDelete = isDelete;
        ItemImgUrl = itemImgUrl;
        ItemValue = itemValue;
        ItemText = itemText;
        KeyId = keyId;
    }

    public int getSeq() {
        return Seq;
    }

    public int getPropertyType() {
        return PropertyType;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public String getItemImgUrl() {
        return ItemImgUrl;
    }

    public String getItemValue() {
        return ItemValue;
    }

    public String getItemText() {
        return ItemText;
    }

    public String getKeyId() {
        return KeyId;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }

    public void setPropertyType(int propertyType) {
        PropertyType = propertyType;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public void setItemImgUrl(String itemImgUrl) {
        ItemImgUrl = itemImgUrl;
    }

    public void setItemValue(String itemValue) {
        ItemValue = itemValue;
    }

    public void setItemText(String itemText) {
        ItemText = itemText;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    @Override
    public String toString() {
        return "PropertyFastSearcherTag{" +
                "Seq=" + Seq +
                ", PropertyType=" + PropertyType +
                ", IsDelete=" + IsDelete +
                ", ItemImgUrl='" + ItemImgUrl + '\'' +
                ", ItemValue='" + ItemValue + '\'' +
                ", ItemText='" + ItemText + '\'' +
                ", KeyId='" + KeyId + '\'' +
                '}';
    }
}
