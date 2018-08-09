package com.centanet.hk.aplus.bean.http;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/3/13.
 */

public class HouseDescription extends LitePalSupport implements Serializable {

    private String UpdatePermisstionsTime = "2010-01-01T00:00:00";
    private int PropertyType = 1;
    private String SortField;
    private String TrustType = "";
    private int PageSize = 15;
    private int PageIndex = 1;
    private String SSDType;
    private String Units;
    private String Floors;
    private String TrustorName;
    private String Mobile;
    private String PropertyNo;
    private String BuildingNames;
    private String Keywords;
    private String PropertySquareType;
    private String SquareFrom;
    private String SquareTo;
    private String SquareUseFrom;
    private String SquareUseTo;
    private String RentPriceFrom;
    private String RentPriceTo;
    private String SalePriceFrom;
    private String SalePriceTo;
    private String SelectType;
    private String SelectKeyId;
    private String Scope;

    private String HasParkingNumber;               //是否車位
    private String IsShowSalePricePremiumUnpaid;   //是否綠表價
    private String IsNoneSSD;                      //IsNoneSSD
    private String HasConfirmTransaction;          //HasConfirmTransaction
    private String HasDevelopmentEndCredits;       //HasDevelopmentEndCredits
    private String HasOptout;                      //OPTOUT盘
    private String IsProxy;                        //委托書
    private String IsHotlist;                      //是否hotlist

    private String HasPropertyKey;

    private String IsOnlyTrust;
    private boolean Ascending = false;
    private boolean IsMobileRequest = true;
    private String PriceUnitType; //均價類型(1:實均售,2:實均租,3:建均售,4:建均租,5:綠表價實均售,6:綠表價建均售)
    private String PriceUnitFrom;
    private String PriceUnitTo;
    private String CompleteYearFrom;
    private String CompleteYearTo;
    private String PropertyDateType;
    private String PropertyDateFrom;
    private String PropertyDateTo;
    private List<String> PropertyStatus;
    private List<String> BuildingTags;
    private List<String> PropertyTypes, IncludedPropertyStatusFrom, IncludedPropertyStatusTo, HouseDirection;
    private List<String> PropertyboolTag, KeywordType, SearcherAddress;
    private String HasSalePricePremiumUnpaid;
    private List<String> DistrictListIds;
    private List<String> BuildingUseTypes;

    public boolean getHasSalePricePremiumUnpaid() {
        return Boolean.parseBoolean(HasSalePricePremiumUnpaid);
    }

    public void setUpdatePermisstionsTime(String updatePermisstionsTime) {
        UpdatePermisstionsTime = updatePermisstionsTime;
    }

    public void setPropertyType(int propertyType) {
        PropertyType = propertyType;
    }

    public void setSortField(String sortField) {
        SortField = sortField;
    }

    public void setTrustType(String trustType) {
        TrustType = trustType;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public void setSSDType(String SSDType) {
        this.SSDType = SSDType;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public void setFloors(String floors) {
        Floors = floors;
    }

    public void setTrustorName(String trustorName) {
        TrustorName = trustorName;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setPropertyNo(String propertyNo) {
        PropertyNo = propertyNo;
    }

    public void setBuildingNames(String buildingNames) {
        BuildingNames = buildingNames;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public void setPropertySquareType(String propertySquareType) {
        PropertySquareType = propertySquareType;
    }

    public void setSquareFrom(String squareFrom) {
        SquareFrom = squareFrom;
    }

    public void setSquareTo(String squareTo) {
        SquareTo = squareTo;
    }

    public void setSquareUseFrom(String squareUseFrom) {
        SquareUseFrom = squareUseFrom;
    }

    public void setSquareUseTo(String squareUseTo) {
        SquareUseTo = squareUseTo;
    }

    public void setRentPriceFrom(String rentPriceFrom) {
        RentPriceFrom = rentPriceFrom;
    }

    public void setRentPriceTo(String rentPriceTo) {
        RentPriceTo = rentPriceTo;
    }

    public void setSalePriceFrom(String salePriceFrom) {
        SalePriceFrom = salePriceFrom;
    }

    public void setSalePriceTo(String salePriceTo) {
        SalePriceTo = salePriceTo;
    }

    public void setSelectType(String selectType) {
        SelectType = selectType;
    }

    public void setSelectKeyId(String selectKeyId) {
        SelectKeyId = selectKeyId;
    }

    public void setScope(String scope) {
        Scope = scope;
    }

    public void setHasPropertyKey(boolean hasPropertyKey) {
        HasPropertyKey = hasPropertyKey + "";
    }

    public void setOnlyTrust(boolean onlyTrust) {
        IsOnlyTrust = "" + onlyTrust;
    }

    public void setOnlyTrust(String onlyTrust) {
        IsOnlyTrust = "" + onlyTrust;
    }

    public void setAscending(boolean ascending) {
        Ascending = ascending;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    public void setPriceUnitType(String priceUnitType) {
        PriceUnitType = priceUnitType;
    }

    public void setPriceUnitFrom(String priceUnitFrom) {
        PriceUnitFrom = priceUnitFrom;
    }

    public void setPriceUnitTo(String priceUnitTo) {
        PriceUnitTo = priceUnitTo;
    }

    public void setCompleteYearFrom(String completeYearFrom) {
        CompleteYearFrom = completeYearFrom;
    }

    public void setCompleteYearTo(String completeYearTo) {
        CompleteYearTo = completeYearTo;
    }

    public void setPropertyDateType(int propertyDateType) {
        PropertyDateType = propertyDateType + "";
    }

    public void setPropertyDateFrom(String propertyDateFrom) {
        PropertyDateFrom = propertyDateFrom;
    }

    public void setPropertyDateTo(String propertyDateTo) {
        PropertyDateTo = propertyDateTo;
    }

    public void setPropertyTypes(List<String> propertyTypes) {
        PropertyTypes = propertyTypes;
    }

    public void setPropertyStatus(List<String> propertyStatus) {
        PropertyStatus = propertyStatus;
    }

    public void setIncludedPropertyStatusFrom(List<String> includedPropertyStatusFrom) {
        IncludedPropertyStatusFrom = includedPropertyStatusFrom;
    }

    public void setIncludedPropertyStatusTo(List<String> includedPropertyStatusTo) {
        IncludedPropertyStatusTo = includedPropertyStatusTo;
    }

    public void setHouseDirection(List<String> houseDirection) {
        HouseDirection = houseDirection;
    }

    public void setPropertyboolTag(List<String> propertyboolTag) {
        PropertyboolTag = propertyboolTag;
    }

    public void setKeywordType(List<String> keywordType) {
        KeywordType = keywordType;
    }

    public void setSearcherAddress(List<String> searcherAddress) {
        SearcherAddress = searcherAddress;
    }

    public void setHasSalePricePremiumUnpaid(boolean hasSalePricePremiumUnpaid) {
        HasSalePricePremiumUnpaid = hasSalePricePremiumUnpaid + "";
    }

    public void setHasParkingNumber(boolean hasParkingNumber) {
        HasParkingNumber = String.valueOf(hasParkingNumber);
    }

    public void setHasParkingNumber(String hasParkingNumber) {
        HasParkingNumber = hasParkingNumber;
    }

    public void setShowSalePricePremiumUnpaid(boolean showSalePricePremiumUnpaid) {
        IsShowSalePricePremiumUnpaid = String.valueOf(showSalePricePremiumUnpaid);
    }

    public void setShowSalePricePremiumUnpaid(String showSalePricePremiumUnpaid) {
        IsShowSalePricePremiumUnpaid = String.valueOf(showSalePricePremiumUnpaid);
    }

    public void setNoneSSD(boolean noneSSD) {
        IsNoneSSD = String.valueOf(noneSSD);
    }

    public void setNoneSSD(String noneSSD) {
        IsNoneSSD = String.valueOf(noneSSD);
    }

    public void setHasConfirmTransaction(boolean hasConfirmTransaction) {
        HasConfirmTransaction = String.valueOf(hasConfirmTransaction);
    }

    public void setHasConfirmTransaction(String hasConfirmTransaction) {
        HasConfirmTransaction = String.valueOf(hasConfirmTransaction);
    }

    public void setHasDevelopmentEndCredits(boolean hasDevelopmentEndCredits) {
        HasDevelopmentEndCredits = String.valueOf(hasDevelopmentEndCredits);
    }

    public void setHasDevelopmentEndCredits(String hasDevelopmentEndCredits) {
        HasDevelopmentEndCredits = hasDevelopmentEndCredits;
    }

    public void setHasOptout(boolean hasOptout) {
        HasOptout = String.valueOf(hasOptout);
    }

    public void setHasOptout(String hasOptout) {
        HasOptout = hasOptout;
    }

    public void setProxy(boolean proxy) {
        IsProxy = String.valueOf(proxy);
    }

    public void setProxy(String proxy) {
        IsProxy = String.valueOf(proxy);
    }

    public void setHotlist(boolean hotlist) {
        IsHotlist = String.valueOf(hotlist);
    }

    public void setHotlist(String hotlist) {
        IsHotlist = String.valueOf(hotlist);
    }

    public void setHasPropertyKey(String hasPropertyKey) {
        HasPropertyKey = hasPropertyKey;
    }

    public void setIsOnlyTrust(String isOnlyTrust) {
        IsOnlyTrust = isOnlyTrust;
    }

    public void setPropertyDateType(String propertyDateType) {
        PropertyDateType = propertyDateType;
    }

    public void setHasSalePricePremiumUnpaid(String hasSalePricePremiumUnpaid) {
        HasSalePricePremiumUnpaid = hasSalePricePremiumUnpaid;
    }

    public void setBuildingUseTypes(List<String> buildingUseTypes) {
        BuildingUseTypes = buildingUseTypes;
    }

    public List<String> getBuildingUseTypes() {
        return BuildingUseTypes;
    }

    public List<String> getBuildingTags() {
        return BuildingTags;
    }

    public void setBuildingTags(List<String> buildingTags) {
        BuildingTags = buildingTags;
    }

    public boolean isHasSalePricePremiumUnpaid() {
        return Boolean.parseBoolean(HasSalePricePremiumUnpaid);
    }

    public boolean isHasParkingNumber() {
        return Boolean.parseBoolean(HasParkingNumber);
    }

    public boolean isShowSalePricePremiumUnpaid() {
        return Boolean.parseBoolean(IsShowSalePricePremiumUnpaid);
    }

    public boolean isNoneSSD() {
        return Boolean.parseBoolean(IsNoneSSD);
    }

    public boolean isHasConfirmTransaction() {
        return Boolean.parseBoolean(HasConfirmTransaction);
    }

    public boolean isHasDevelopmentEndCredits() {
        return Boolean.parseBoolean(HasDevelopmentEndCredits);
    }

    public boolean isHasOptout() {
        return Boolean.parseBoolean(HasOptout);
    }

    public boolean isProxy() {
        return Boolean.parseBoolean(IsProxy);
    }

    public boolean isHotlist() {
        return Boolean.parseBoolean(IsHotlist);
    }

    public List<String> getDistrictListIds() {
        return DistrictListIds;
    }

    public void setDistrictListIds(List<String> districtListIds) {
        DistrictListIds = districtListIds;
    }

    public String getIsOnlyTrust() {
        return IsOnlyTrust;
    }

    public String getUpdatePermisstionsTime() {
        return UpdatePermisstionsTime;
    }

    public int getPropertyType() {
        return PropertyType;
    }

    public String getSortField() {
        return SortField;
    }

    public String getTrustType() {
        return TrustType;
    }

    public int getPageSize() {
        return PageSize;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public String getSSDType() {
        return SSDType;
    }

    public String getUnits() {
        return Units;
    }

    public String getFloors() {
        return Floors;
    }

    public String getTrustorName() {
        return TrustorName;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getPropertyNo() {
        return PropertyNo;
    }

    public String getBuildingNames() {
        return BuildingNames;
    }

    public String getKeywords() {
        return Keywords;
    }

    public String getPropertySquareType() {
        return PropertySquareType;
    }

    public String getSquareFrom() {
        return SquareFrom;
    }

    public String getSquareTo() {
        return SquareTo;
    }

    public String getSquareUseFrom() {
        return SquareUseFrom;
    }

    public String getSquareUseTo() {
        return SquareUseTo;
    }

    public String getRentPriceFrom() {
        return RentPriceFrom;
    }

    public String getRentPriceTo() {
        return RentPriceTo;
    }

    public String getSalePriceFrom() {
        return SalePriceFrom;
    }

    public String getSalePriceTo() {
        return SalePriceTo;
    }

    public String getSelectType() {
        return SelectType;
    }

    public String getSelectKeyId() {
        return SelectKeyId;
    }

    public String getScope() {
        return Scope;
    }

    public boolean getHasPropertyKey() {
        return Boolean.parseBoolean(HasPropertyKey);
    }

    public boolean isOnlyTrust() {
        return Boolean.parseBoolean(IsOnlyTrust);
    }

    public boolean isAscending() {
        return Ascending;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    public String getPriceUnitType() {
        return PriceUnitType;
    }

    public String getPriceUnitFrom() {
        return PriceUnitFrom;
    }

    public String getPriceUnitTo() {
        return PriceUnitTo;
    }

    public String getCompleteYearFrom() {
        return CompleteYearFrom;
    }

    public String getCompleteYearTo() {
        return CompleteYearTo;
    }

    public String getPropertyDateType() {
        return PropertyDateType;
    }

    public String getPropertyDateFrom() {
        return PropertyDateFrom;
    }

    public String getPropertyDateTo() {
        return PropertyDateTo;
    }

    public List<String> getPropertyTypes() {
        return PropertyTypes;
    }

    public List<String> getPropertyStatus() {
        return PropertyStatus;
    }

    public List<String> getIncludedPropertyStatusFrom() {
        return IncludedPropertyStatusFrom;
    }

    public List<String> getIncludedPropertyStatusTo() {
        return IncludedPropertyStatusTo;
    }

    public List<String> getHouseDirection() {
        return HouseDirection;
    }

    public List<String> getPropertyboolTag() {
        return PropertyboolTag;
    }

    public List<String> getKeywordType() {
        return KeywordType;
    }

    public List<String> getSearcherAddress() {
        return SearcherAddress;
    }

    @Override
    public String toString() {
        return "HouseDescription{" +
                "UpdatePermisstionsTime='" + UpdatePermisstionsTime + '\'' +
                ", PropertyType=" + PropertyType +
                ", SortField='" + SortField + '\'' +
                ", TrustType='" + TrustType + '\'' +
                ", PageSize=" + PageSize +
                ", PageIndex=" + PageIndex +
                ", SSDType='" + SSDType + '\'' +
                ", Units='" + Units + '\'' +
                ", Floors='" + Floors + '\'' +
                ", TrustorName='" + TrustorName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", PropertyNo='" + PropertyNo + '\'' +
                ", BuildingNames='" + BuildingNames + '\'' +
                ", Keywords='" + Keywords + '\'' +
                ", PropertySquareType='" + PropertySquareType + '\'' +
                ", SquareFrom='" + SquareFrom + '\'' +
                ", SquareTo='" + SquareTo + '\'' +
                ", SquareUseFrom='" + SquareUseFrom + '\'' +
                ", SquareUseTo='" + SquareUseTo + '\'' +
                ", RentPriceFrom='" + RentPriceFrom + '\'' +
                ", RentPriceTo='" + RentPriceTo + '\'' +
                ", SalePriceFrom='" + SalePriceFrom + '\'' +
                ", SalePriceTo='" + SalePriceTo + '\'' +
                ", SelectType='" + SelectType + '\'' +
                ", SelectKeyId='" + SelectKeyId + '\'' +
                ", Scope='" + Scope + '\'' +
                ", HasParkingNumber='" + HasParkingNumber + '\'' +
                ", IsShowSalePricePremiumUnpaid='" + IsShowSalePricePremiumUnpaid + '\'' +
                ", IsNoneSSD='" + IsNoneSSD + '\'' +
                ", HasConfirmTransaction='" + HasConfirmTransaction + '\'' +
                ", HasDevelopmentEndCredits='" + HasDevelopmentEndCredits + '\'' +
                ", HasOptout='" + HasOptout + '\'' +
                ", IsProxy='" + IsProxy + '\'' +
                ", IsHotlist='" + IsHotlist + '\'' +
                ", HasPropertyKey='" + HasPropertyKey + '\'' +
                ", IsOnlyTrust='" + IsOnlyTrust + '\'' +
                ", Ascending=" + Ascending +
                ", IsMobileRequest=" + IsMobileRequest +
                ", PriceUnitType='" + PriceUnitType + '\'' +
                ", PriceUnitFrom='" + PriceUnitFrom + '\'' +
                ", PriceUnitTo='" + PriceUnitTo + '\'' +
                ", CompleteYearFrom='" + CompleteYearFrom + '\'' +
                ", CompleteYearTo='" + CompleteYearTo + '\'' +
                ", PropertyDateType='" + PropertyDateType + '\'' +
                ", PropertyDateFrom='" + PropertyDateFrom + '\'' +
                ", PropertyDateTo='" + PropertyDateTo + '\'' +
                ", PropertyStatus=" + PropertyStatus +
                ", BuildingTags=" + BuildingTags +
                ", PropertyTypes=" + PropertyTypes +
                ", IncludedPropertyStatusFrom=" + IncludedPropertyStatusFrom +
                ", IncludedPropertyStatusTo=" + IncludedPropertyStatusTo +
                ", HouseDirection=" + HouseDirection +
                ", PropertyboolTag=" + PropertyboolTag +
                ", KeywordType=" + KeywordType +
                ", SearcherAddress=" + SearcherAddress +
                ", HasSalePricePremiumUnpaid='" + HasSalePricePremiumUnpaid + '\'' +
                ", DistrictListIds=" + DistrictListIds +
                ", BuildingUseTypes=" + BuildingUseTypes +
                '}';
    }


}
