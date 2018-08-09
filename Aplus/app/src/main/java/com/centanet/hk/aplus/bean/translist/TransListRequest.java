package com.centanet.hk.aplus.bean.translist;

import java.util.List;

/**
 * Created by yangzm4 on 2018/8/7.
 */

public class TransListRequest {

    private String UpdatePermisstionsTime = "2010-01-01T00:00:00";
    private String PropertyType = "16";
    private List<String> DistrictListIds;
    private String SearcherAddress;
    private String Floors;
    private String Units;
    private String RentPriceFrom;
    private String RentPriceTo;
    private String SellPriceFrom;
    private String SellPriceTo;
    private String SquareUseFrom;
    private String SquareUseTo;
    private String SquareFrom;
    private String SquareTo;
    private String TrusactionDate;
    private String TransactionDateFrom;
    private String TransactionDateTo;
    private String PrelimDateFrom;
    private String PrelimDateTo;
    private String FormalDateFrom;
    private String FormalDateTo;
    private String CompleteDateFrom;
    private String CompleteDateTo;
    private String RentDateFrom;
    private String RentDateTo;
    private String ContactSearchType;
    private String ContactName;
    private String ContactValue;
    private List<String> BuildingUsages;
    private List<String> RoomCounts;
    private String TransactionTypes;
    private String IsTransferred;
    private String IsConfirmed;
    private String IsOStatus;
    private String IsCorporationTransferre;
    private String IsDevelopmentEndCredits;
    private String IsSalePricePremiumUnpaid;
    private String PriceUnitType;
    private String PriceUnitFrom;
    private String PriceUnitTo;
    private int PageIndex = 1;
    private int PageSize = 15;
    private String SortField = "";
    private boolean Ascending = false;
    private boolean IsMobileRequest = true;

    public TransListRequest() {
    }

    public TransListRequest(String updatePermisstionsTime, String propertyType, List<String> districtListIds, String searcherAddress, String floors, String units, String rentPriceFrom, String rentPriceTo, String sellPriceFrom, String sellPriceTo, String squareUseFrom, String squareUseTo, String squareFrom, String squareTo, String trusactionDate, String transactionDateFrom, String transactionDateTo, String prelimDateFrom, String prelimDateTo, String formalDateFrom, String formalDateTo, String completeDateFrom, String completeDateTo, String rentDateFrom, String rentDateTo, String contactSearchType, String contactName, String contactValue, List<String> buildingUsages, List<String> roomCounts, String transactionTypes, String isTransferred, String isConfirmed, String isOStatus, String isCorporationTransferre, String isDevelopmentEndCredits, String isSalePricePremiumUnpaid, String priceUnitType, String priceUnitFrom, String priceUnitTo, int pageIndex, int pageSize, String sortField, boolean ascending, boolean isMobileRequest) {
        UpdatePermisstionsTime = updatePermisstionsTime;
        PropertyType = propertyType;
        DistrictListIds = districtListIds;
        SearcherAddress = searcherAddress;
        Floors = floors;
        Units = units;
        RentPriceFrom = rentPriceFrom;
        RentPriceTo = rentPriceTo;
        SellPriceFrom = sellPriceFrom;
        SellPriceTo = sellPriceTo;
        SquareUseFrom = squareUseFrom;
        SquareUseTo = squareUseTo;
        SquareFrom = squareFrom;
        SquareTo = squareTo;
        TrusactionDate = trusactionDate;
        TransactionDateFrom = transactionDateFrom;
        TransactionDateTo = transactionDateTo;
        PrelimDateFrom = prelimDateFrom;
        PrelimDateTo = prelimDateTo;
        FormalDateFrom = formalDateFrom;
        FormalDateTo = formalDateTo;
        CompleteDateFrom = completeDateFrom;
        CompleteDateTo = completeDateTo;
        RentDateFrom = rentDateFrom;
        RentDateTo = rentDateTo;
        ContactSearchType = contactSearchType;
        ContactName = contactName;
        ContactValue = contactValue;
        BuildingUsages = buildingUsages;
        RoomCounts = roomCounts;
        TransactionTypes = transactionTypes;
        IsTransferred = isTransferred;
        IsConfirmed = isConfirmed;
        IsOStatus = isOStatus;
        IsCorporationTransferre = isCorporationTransferre;
        IsDevelopmentEndCredits = isDevelopmentEndCredits;
        IsSalePricePremiumUnpaid = isSalePricePremiumUnpaid;
        PriceUnitType = priceUnitType;
        PriceUnitFrom = priceUnitFrom;
        PriceUnitTo = priceUnitTo;
        PageIndex = pageIndex;
        PageSize = pageSize;
        SortField = sortField;
        Ascending = ascending;
        IsMobileRequest = isMobileRequest;
    }

    public String getUpdatePermisstionsTime() {
        return UpdatePermisstionsTime;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public List<String> getDistrictListIds() {
        return DistrictListIds;
    }

    public String getSearcherAddress() {
        return SearcherAddress;
    }

    public String getFloors() {
        return Floors;
    }

    public String getUnits() {
        return Units;
    }

    public String getRentPriceFrom() {
        return RentPriceFrom;
    }

    public String getRentPriceTo() {
        return RentPriceTo;
    }

    public String getSellPriceFrom() {
        return SellPriceFrom;
    }

    public String getSellPriceTo() {
        return SellPriceTo;
    }

    public String getSquareUseFrom() {
        return SquareUseFrom;
    }

    public String getSquareUseTo() {
        return SquareUseTo;
    }

    public String getSquareFrom() {
        return SquareFrom;
    }

    public String getSquareTo() {
        return SquareTo;
    }

    public String getTrusactionDate() {
        return TrusactionDate;
    }

    public String getTransactionDateFrom() {
        return TransactionDateFrom;
    }

    public String getTransactionDateTo() {
        return TransactionDateTo;
    }

    public String getPrelimDateFrom() {
        return PrelimDateFrom;
    }

    public String getPrelimDateTo() {
        return PrelimDateTo;
    }

    public String getFormalDateFrom() {
        return FormalDateFrom;
    }

    public String getFormalDateTo() {
        return FormalDateTo;
    }

    public String getCompleteDateFrom() {
        return CompleteDateFrom;
    }

    public String getCompleteDateTo() {
        return CompleteDateTo;
    }

    public String getRentDateFrom() {
        return RentDateFrom;
    }

    public String getRentDateTo() {
        return RentDateTo;
    }

    public String getContactSearchType() {
        return ContactSearchType;
    }

    public String getContactName() {
        return ContactName;
    }

    public String getContactValue() {
        return ContactValue;
    }

    public List<String> getBuildingUsages() {
        return BuildingUsages;
    }

    public List<String> getRoomCounts() {
        return RoomCounts;
    }

    public String getTransactionTypes() {
        return TransactionTypes;
    }

    public String getIsTransferred() {
        return IsTransferred;
    }

    public String getIsConfirmed() {
        return IsConfirmed;
    }

    public String getIsOStatus() {
        return IsOStatus;
    }

    public String getIsCorporationTransferre() {
        return IsCorporationTransferre;
    }

    public String getIsDevelopmentEndCredits() {
        return IsDevelopmentEndCredits;
    }

    public String getIsSalePricePremiumUnpaid() {
        return IsSalePricePremiumUnpaid;
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

    public int getPageIndex() {
        return PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public String getSortField() {
        return SortField;
    }

    public boolean isAscending() {
        return Ascending;
    }

    public boolean isMobileRequest() {
        return IsMobileRequest;
    }

    public void setUpdatePermisstionsTime(String updatePermisstionsTime) {
        UpdatePermisstionsTime = updatePermisstionsTime;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public void setDistrictListIds(List<String> districtListIds) {
        DistrictListIds = districtListIds;
    }

    public void setSearcherAddress(String searcherAddress) {
        SearcherAddress = searcherAddress;
    }

    public void setFloors(String floors) {
        Floors = floors;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public void setRentPriceFrom(String rentPriceFrom) {
        RentPriceFrom = rentPriceFrom;
    }

    public void setRentPriceTo(String rentPriceTo) {
        RentPriceTo = rentPriceTo;
    }

    public void setSellPriceFrom(String sellPriceFrom) {
        SellPriceFrom = sellPriceFrom;
    }

    public void setSellPriceTo(String sellPriceTo) {
        SellPriceTo = sellPriceTo;
    }

    public void setSquareUseFrom(String squareUseFrom) {
        SquareUseFrom = squareUseFrom;
    }

    public void setSquareUseTo(String squareUseTo) {
        SquareUseTo = squareUseTo;
    }

    public void setSquareFrom(String squareFrom) {
        SquareFrom = squareFrom;
    }

    public void setSquareTo(String squareTo) {
        SquareTo = squareTo;
    }

    public void setTrusactionDate(String trusactionDate) {
        TrusactionDate = trusactionDate;
    }

    public void setTransactionDateFrom(String transactionDateFrom) {
        TransactionDateFrom = transactionDateFrom;
    }

    public void setTransactionDateTo(String transactionDateTo) {
        TransactionDateTo = transactionDateTo;
    }

    public void setPrelimDateFrom(String prelimDateFrom) {
        PrelimDateFrom = prelimDateFrom;
    }

    public void setPrelimDateTo(String prelimDateTo) {
        PrelimDateTo = prelimDateTo;
    }

    public void setFormalDateFrom(String formalDateFrom) {
        FormalDateFrom = formalDateFrom;
    }

    public void setFormalDateTo(String formalDateTo) {
        FormalDateTo = formalDateTo;
    }

    public void setCompleteDateFrom(String completeDateFrom) {
        CompleteDateFrom = completeDateFrom;
    }

    public void setCompleteDateTo(String completeDateTo) {
        CompleteDateTo = completeDateTo;
    }

    public void setRentDateFrom(String rentDateFrom) {
        RentDateFrom = rentDateFrom;
    }

    public void setRentDateTo(String rentDateTo) {
        RentDateTo = rentDateTo;
    }

    public void setContactSearchType(String contactSearchType) {
        ContactSearchType = contactSearchType;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public void setContactValue(String contactValue) {
        ContactValue = contactValue;
    }

    public void setBuildingUsages(List<String> buildingUsages) {
        BuildingUsages = buildingUsages;
    }

    public void setRoomCounts(List<String> roomCounts) {
        RoomCounts = roomCounts;
    }

    public void setTransactionTypes(String transactionTypes) {
        TransactionTypes = transactionTypes;
    }

    public void setIsTransferred(String isTransferred) {
        IsTransferred = isTransferred;
    }

    public void setIsConfirmed(String isConfirmed) {
        IsConfirmed = isConfirmed;
    }

    public void setIsOStatus(String isOStatus) {
        IsOStatus = isOStatus;
    }

    public void setIsCorporationTransferre(String isCorporationTransferre) {
        IsCorporationTransferre = isCorporationTransferre;
    }

    public void setIsDevelopmentEndCredits(String isDevelopmentEndCredits) {
        IsDevelopmentEndCredits = isDevelopmentEndCredits;
    }

    public void setIsSalePricePremiumUnpaid(String isSalePricePremiumUnpaid) {
        IsSalePricePremiumUnpaid = isSalePricePremiumUnpaid;
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

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public void setSortField(String sortField) {
        SortField = sortField;
    }

    public void setAscending(boolean ascending) {
        Ascending = ascending;
    }

    public void setMobileRequest(boolean mobileRequest) {
        IsMobileRequest = mobileRequest;
    }

    @Override
    public String toString() {
        return "TransListRequest{" +
                "UpdatePermisstionsTime='" + UpdatePermisstionsTime + '\'' +
                ", PropertyType='" + PropertyType + '\'' +
                ", DistrictListIds=" + DistrictListIds +
                ", SearcherAddress='" + SearcherAddress + '\'' +
                ", Floors='" + Floors + '\'' +
                ", Units='" + Units + '\'' +
                ", RentPriceFrom='" + RentPriceFrom + '\'' +
                ", RentPriceTo='" + RentPriceTo + '\'' +
                ", SellPriceFrom='" + SellPriceFrom + '\'' +
                ", SellPriceTo='" + SellPriceTo + '\'' +
                ", SquareUseFrom='" + SquareUseFrom + '\'' +
                ", SquareUseTo='" + SquareUseTo + '\'' +
                ", SquareFrom='" + SquareFrom + '\'' +
                ", SquareTo='" + SquareTo + '\'' +
                ", TrusactionDate='" + TrusactionDate + '\'' +
                ", TransactionDateFrom='" + TransactionDateFrom + '\'' +
                ", TransactionDateTo='" + TransactionDateTo + '\'' +
                ", PrelimDateFrom='" + PrelimDateFrom + '\'' +
                ", PrelimDateTo='" + PrelimDateTo + '\'' +
                ", FormalDateFrom='" + FormalDateFrom + '\'' +
                ", FormalDateTo='" + FormalDateTo + '\'' +
                ", CompleteDateFrom='" + CompleteDateFrom + '\'' +
                ", CompleteDateTo='" + CompleteDateTo + '\'' +
                ", RentDateFrom='" + RentDateFrom + '\'' +
                ", RentDateTo='" + RentDateTo + '\'' +
                ", ContactSearchType='" + ContactSearchType + '\'' +
                ", ContactName='" + ContactName + '\'' +
                ", ContactValue='" + ContactValue + '\'' +
                ", BuildingUsages=" + BuildingUsages +
                ", RoomCounts=" + RoomCounts +
                ", TransactionTypes='" + TransactionTypes + '\'' +
                ", IsTransferred='" + IsTransferred + '\'' +
                ", IsConfirmed='" + IsConfirmed + '\'' +
                ", IsOStatus='" + IsOStatus + '\'' +
                ", IsCorporationTransferre='" + IsCorporationTransferre + '\'' +
                ", IsDevelopmentEndCredits='" + IsDevelopmentEndCredits + '\'' +
                ", IsSalePricePremiumUnpaid='" + IsSalePricePremiumUnpaid + '\'' +
                ", PriceUnitType='" + PriceUnitType + '\'' +
                ", PriceUnitFrom='" + PriceUnitFrom + '\'' +
                ", PriceUnitTo='" + PriceUnitTo + '\'' +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", SortField='" + SortField + '\'' +
                ", Ascending=" + Ascending +
                ", IsMobileRequest=" + IsMobileRequest +
                '}';
    }
}
