package com.centanet.hk.aplus.bean.house;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/1/30.
 */

public class Properties implements Serializable {

    private String PropertyStatusKeyId;
    private String SquareFoot;
    private String SquareUseFoot;
    private String SquareUseSourceNum;
    private String HouseDirection;
    private String SalePrice;
    private String RentPrice;
    private int PropertyOnlyTrustType;
    private String PropertyNo;
    private String PropertyStatus;
    private String PropertyStatusCode;
    private int TrustType;
    private String EstateName;
    private String BuildingName;
    private String HouseNo;
    private String Floor;
    private String PropertyInterval;
    private boolean FavoriteFlag;
    private int PropertyStatusCategory;
    private String SalePriceUnit;
    private String ActualSalePriceUnit;
    private String RentPriceUnit;
    private String ActualRentPriceUnit;
    private boolean IsODish;
    private boolean DevelopmentEndCredits;
    private String SalePricePremiumUnpaid;
    private boolean IsConfirmed;
    private int SSDType;
    private boolean IsOnlyTrust;
    private String HotList;
    private int PropertyKeyEnum;


    public Properties() {
    }

    public Properties(String propertyStatusKeyId, String squareFoot, String squareUseSourceNum, String houseDirection, String salePrice, String rentPrice, int propertyOnlyTrustType, String propertyNo, String propertyStatus, String propertyStatusCode, int trustType, String estateName, String buildingName, String houseNo, String propertyInterval, boolean favoriteFlag, int propertyStatusCategory, String salePriceUnit, String actualSalePriceUnit, String rentPriceUnit, String actualRentPriceUnit, boolean isODish, boolean developmentEndCredits, boolean isConfirmed, int SSDType, boolean isOnlyTrust, String hotList, int propertyKeyEnum, String prompt, String registerDate, String lastFollowDate, String keyId) {
        PropertyStatusKeyId = propertyStatusKeyId;
        SquareFoot = squareFoot;
        SquareUseSourceNum = squareUseSourceNum;
        HouseDirection = houseDirection;
        SalePrice = salePrice;
        RentPrice = rentPrice;
        PropertyOnlyTrustType = propertyOnlyTrustType;
        PropertyNo = propertyNo;
        PropertyStatus = propertyStatus;
        PropertyStatusCode = propertyStatusCode;
        TrustType = trustType;
        EstateName = estateName;
        BuildingName = buildingName;
        HouseNo = houseNo;
        PropertyInterval = propertyInterval;
        FavoriteFlag = favoriteFlag;
        PropertyStatusCategory = propertyStatusCategory;
        SalePriceUnit = salePriceUnit;
        ActualSalePriceUnit = actualSalePriceUnit;
        RentPriceUnit = rentPriceUnit;
        ActualRentPriceUnit = actualRentPriceUnit;
        IsODish = isODish;
        DevelopmentEndCredits = developmentEndCredits;
        IsConfirmed = isConfirmed;
        this.SSDType = SSDType;
        IsOnlyTrust = isOnlyTrust;
        HotList = hotList;
        PropertyKeyEnum = propertyKeyEnum;
        Prompt = prompt;
        RegisterDate = registerDate;
        LastFollowDate = lastFollowDate;
        KeyId = keyId;
    }

    public void setActualRentPriceUnit(String actualRentPriceUnit) {
        ActualRentPriceUnit = actualRentPriceUnit;
    }

    public void setODish(boolean ODish) {
        IsODish = ODish;
    }

    public void setDevelopmentEndCredits(boolean developmentEndCredits) {
        DevelopmentEndCredits = developmentEndCredits;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getFloor() {
        return Floor;
    }

    public String getSquareUseFoot() {
        return SquareUseFoot;
    }

    public void setSquareUseFoot(String squareUseFoot) {
        SquareUseFoot = squareUseFoot;
    }

    public void setConfirmed(boolean confirmed) {
        IsConfirmed = confirmed;
    }

    public boolean getIsConfirmed() {
        return IsConfirmed;
    }

    public void setSSDType(int SSDType) {
        this.SSDType = SSDType;
    }

    public void setOnlyTrust(boolean onlyTrust) {
        IsOnlyTrust = onlyTrust;
    }

    public void setHotList(String hotList) {
        HotList = hotList;
    }

    public void setPropertyKeyEnum(int propertyKeyEnum) {
        PropertyKeyEnum = propertyKeyEnum;
    }

    public void setPrompt(String prompt) {
        Prompt = prompt;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public void setLastFollowDate(String lastFollowDate) {
        LastFollowDate = lastFollowDate;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getActualRentPriceUnit() {
        return ActualRentPriceUnit;
    }

    public boolean isODish() {
        return IsODish;
    }

    public boolean getDevelopmentEndCredits() {
        return DevelopmentEndCredits;
    }

    public int getSSDType() {
        return SSDType;
    }

    public boolean isOnlyTrust() {
        return IsOnlyTrust;
    }

    public String getHotList() {
        return HotList;
    }

    public int getPropertyKeyEnum() {
        return PropertyKeyEnum;
    }

    public String getPrompt() {
        return Prompt;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public String getLastFollowDate() {
        return LastFollowDate;
    }

    public String getKeyId() {
        return KeyId;
    }

    private String Prompt;
    private String RegisterDate;
    private String LastFollowDate;
    private String KeyId;

    public String getPropertyStatusKeyId() {
        return PropertyStatusKeyId;
    }

    public String getSquareFoot() {
        return SquareFoot;
    }

    public String getSquareUseSourceNum() {
        return SquareUseSourceNum;
    }

    public String getHouseDirection() {
        return HouseDirection;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public String getRentPrice() {
        return RentPrice;
    }

    public int getPropertyOnlyTrustType() {
        return PropertyOnlyTrustType;
    }

    public String getPropertyNo() {
        return PropertyNo;
    }

    public String getPropertyStatus() {
        return PropertyStatus;
    }

    public String getPropertyStatusCode() {
        return PropertyStatusCode;
    }

    public int getTrustType() {
        return TrustType;
    }

    public String getEstateName() {
        return EstateName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public String getPropertyInterval() {
        return PropertyInterval;
    }

    public boolean isFavoriteFlag() {
        return FavoriteFlag;
    }

    public int getPropertyStatusCategory() {
        return PropertyStatusCategory;
    }

    public String getSalePriceUnit() {
        return SalePriceUnit;
    }

    public String getActualSalePriceUnit() {
        return ActualSalePriceUnit;
    }

    public String getRentPriceUnit() {
        return RentPriceUnit;
    }

    public String getSalePricePremiumUnpaid() {
        return SalePricePremiumUnpaid;
    }

    public void setSalePricePremiumUnpaid(String salePricePremiumUnpaid) {
        SalePricePremiumUnpaid = salePricePremiumUnpaid;
    }

    public void setPropertyStatusKeyId(String propertyStatusKeyId) {
        PropertyStatusKeyId = propertyStatusKeyId;
    }

    public void setSquareFoot(String squareFoot) {
        SquareFoot = squareFoot;
    }

    public void setSquareUseSourceNum(String squareUseSourceNum) {
        SquareUseSourceNum = squareUseSourceNum;
    }

    public void setHouseDirection(String houseDirection) {
        HouseDirection = houseDirection;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public void setRentPrice(String rentPrice) {
        RentPrice = rentPrice;
    }

    public void setPropertyOnlyTrustType(int propertyOnlyTrustType) {
        PropertyOnlyTrustType = propertyOnlyTrustType;
    }

    public void setPropertyNo(String propertyNo) {
        PropertyNo = propertyNo;
    }

    public void setPropertyStatus(String propertyStatus) {
        PropertyStatus = propertyStatus;
    }

    public void setPropertyStatusCode(String propertyStatusCode) {
        PropertyStatusCode = propertyStatusCode;
    }

    public void setTrustType(int trustType) {
        TrustType = trustType;
    }

    public void setEstateName(String estateName) {
        EstateName = estateName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public void setPropertyInterval(String propertyInterval) {
        PropertyInterval = propertyInterval;
    }

    public void setFavoriteFlag(boolean favoriteFlag) {
        FavoriteFlag = favoriteFlag;
    }

    public void setPropertyStatusCategory(int propertyStatusCategory) {
        PropertyStatusCategory = propertyStatusCategory;
    }

    public void setSalePriceUnit(String salePriceUnit) {
        SalePriceUnit = salePriceUnit;
    }

    public void setActualSalePriceUnit(String actualSalePriceUnit) {
        ActualSalePriceUnit = actualSalePriceUnit;
    }

    public void setRentPriceUnit(String rentPriceUnit) {
        RentPriceUnit = rentPriceUnit;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "PropertyStatusKeyId='" + PropertyStatusKeyId + '\'' +
                ", SquareFoot=" + SquareFoot +
                ", SquareUseSourceNum=" + SquareUseSourceNum +
                ", HouseDirection='" + HouseDirection + '\'' +
                ", SalePrice=" + SalePrice +
                ", RentPrice=" + RentPrice +
                ", PropertyOnlyTrustType=" + PropertyOnlyTrustType +
                ", PropertyNo='" + PropertyNo + '\'' +
                ", PropertyStatus='" + PropertyStatus + '\'' +
                ", PropertyStatusCode='" + PropertyStatusCode + '\'' +
                ", TrustType=" + TrustType +
                ", EstateName='" + EstateName + '\'' +
                ", BuildingName='" + BuildingName + '\'' +
                ", HouseNo='" + HouseNo + '\'' +
                ", PropertyInterval='" + PropertyInterval + '\'' +
                ", FavoriteFlag=" + FavoriteFlag +
                ", PropertyStatusCategory=" + PropertyStatusCategory +
                ", SalePriceUnit='" + SalePriceUnit + '\'' +
                ", ActualSalePriceUnit='" + ActualSalePriceUnit + '\'' +
                ", RentPriceUnit='" + RentPriceUnit + '\'' +
                ", ActualRentPriceUnit='" + ActualRentPriceUnit + '\'' +
                ", IsODish=" + IsODish +
                ", DevelopmentEndCredits=" + DevelopmentEndCredits +
                ", IsConfirmed=" + IsConfirmed +
                ", SSDType=" + SSDType +
                ", IsOnlyTrust=" + IsOnlyTrust +
                ", HotList='" + HotList + '\'' +
                ", PropertyKeyEnum=" + PropertyKeyEnum +
                ", Prompt='" + Prompt + '\'' +
                ", RegisterDate='" + RegisterDate + '\'' +
                ", LastFollowDate='" + LastFollowDate + '\'' +
                ", KeyId='" + KeyId + '\'' +
                '}';
    }
}
