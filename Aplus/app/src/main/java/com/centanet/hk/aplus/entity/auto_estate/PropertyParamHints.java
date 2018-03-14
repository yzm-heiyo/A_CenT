package com.centanet.hk.aplus.entity.auto_estate;

import org.litepal.crud.DataSupport;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class PropertyParamHints extends DataSupport{

    private String KeyId;
    private int KeyIdType;
    private String AddressName;
    private String EnAddressName;
    private String DistrictName;
    private String AreaName;
    private String NameSpell;
    private String OrderBy;
    private int Sort;
    private int NameMaxLength;

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public void setKeyIdType(int keyIdType) {
        KeyIdType = keyIdType;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public void setEnAddressName(String enAddressName) {
        EnAddressName = enAddressName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public void setNameSpell(String nameSpell) {
        NameSpell = nameSpell;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    public void setNameMaxLength(int nameMaxLength) {
        NameMaxLength = nameMaxLength;
    }

    public String getKeyId() {
        return KeyId;
    }

    public int getKeyIdType() {
        return KeyIdType;
    }

    public String getAddressName() {
        return AddressName;
    }

    public String getEnAddressName() {
        return EnAddressName;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getNameSpell() {
        return NameSpell;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public int getSort() {
        return Sort;
    }

    public int getNameMaxLength() {
        return NameMaxLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertyParamHints that = (PropertyParamHints) o;

        if (KeyIdType != that.KeyIdType) return false;
        if (Sort != that.Sort) return false;
        if (NameMaxLength != that.NameMaxLength) return false;
        if (KeyId != null ? !KeyId.equals(that.KeyId) : that.KeyId != null) return false;
        if (AddressName != null ? !AddressName.equals(that.AddressName) : that.AddressName != null)
            return false;
        if (EnAddressName != null ? !EnAddressName.equals(that.EnAddressName) : that.EnAddressName != null)
            return false;
        if (DistrictName != null ? !DistrictName.equals(that.DistrictName) : that.DistrictName != null)
            return false;
        if (AreaName != null ? !AreaName.equals(that.AreaName) : that.AreaName != null)
            return false;
        if (NameSpell != null ? !NameSpell.equals(that.NameSpell) : that.NameSpell != null)
            return false;
        return OrderBy != null ? OrderBy.equals(that.OrderBy) : that.OrderBy == null;
    }

    @Override
    public int hashCode() {
        int result = KeyId != null ? KeyId.hashCode() : 0;
        result = 31 * result + KeyIdType;
        result = 31 * result + (AddressName != null ? AddressName.hashCode() : 0);
        result = 31 * result + (EnAddressName != null ? EnAddressName.hashCode() : 0);
        result = 31 * result + (DistrictName != null ? DistrictName.hashCode() : 0);
        result = 31 * result + (AreaName != null ? AreaName.hashCode() : 0);
        result = 31 * result + (NameSpell != null ? NameSpell.hashCode() : 0);
        result = 31 * result + (OrderBy != null ? OrderBy.hashCode() : 0);
        result = 31 * result + Sort;
        result = 31 * result + NameMaxLength;
        return result;
    }

    @Override
    public String toString() {
        return "PropertyParamHints{" +
                "KeyId='" + KeyId + '\'' +
                ", KeyIdType=" + KeyIdType +
                ", AddressName='" + AddressName + '\'' +
                ", EnAddressName='" + EnAddressName + '\'' +
                ", DistrictName='" + DistrictName + '\'' +
                ", AreaName='" + AreaName + '\'' +
                ", NameSpell='" + NameSpell + '\'' +
                ", OrderBy='" + OrderBy + '\'' +
                ", Sort=" + Sort +
                ", NameMaxLength=" + NameMaxLength +
                '}';
    }
}
