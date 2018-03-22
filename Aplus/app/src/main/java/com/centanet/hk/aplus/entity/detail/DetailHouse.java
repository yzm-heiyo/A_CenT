package com.centanet.hk.aplus.entity.detail;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailHouse implements Serializable {
    private boolean UserIsShowAddressDetail;
    private String DetailAddressEnNoFoolrInfo;
    private String DetailAddressChNoFoolrInfo;
    private String DetailAddressChInfo;
    private String PropertyNo;
    private String PropertyStatus;
    private String PropertyStatusCode;
    private boolean IsODish;
    private boolean HasOnlyTrust;
    private String PropertyProprietors;
    private String DevelopmentEndCredits;
    private boolean IsConfirmed;
    private int SSDType;
    private String SSDInfo;
    private boolean IsFavorite;
    private String HotList;
    private String PropertyCustomerNames;
    private String PropertyBuildingOwner;
    private String SaleFloorPrice;
    private String OldSalePrice;
    private String RentFloorPrice;
    private String SaleFloorPriceFormate;
    private String RentFloorPriceFormate;
    private int TakeSeeCount;
    private String DistrictName;
    private String AreaName;
    private String SquareFoot;
    private String SquareUseFoot;
    private String SquareSource;
    private String SquareUseSource;
    private String SalePrice;
    private String RentPrice;
    private String SalePriceUnit;
    private String ActualSalePriceUnit;
    private String RentPriceUnit;
    private String ActualRentPriceUnit;
    private String PowerOfAttorneyThree;
    private String PowerOfAttorneyFive;
    private String EstimatedDate;
    private String ProvideDate;
    private String RegisterDate;
    private String UtilityRatio;
    private String Prompt;
    private String PropertyKeyNo;
    private String HouseDirection;
    private String PropertyInterval;
    private String PropertyTags;
    private String PropertyUsage;
    private String PropertyNote;
    private String AllFloor;
    private String ParkingNumber;
    private String DepartmentPermissions;
    private String PropertySource;
    private String Supply;
    private String CustomField1Name;
    private String CustomField1;
    private String CustomField2Name;
    private String CustomField2;
    private String CustomField3Name;
    private String CustomField3;
    private String Remark;
    private String SearchDate;
    private String AccessementNo;
    private String CompleteYear;
    private String MgrFee;
    private String PropertyChiefKeyId;
    private String PropertyChiefDepartmentKeyId;
    private String PropertyTraderKeyId;
    private String PropertyTraderDepartmentKeyId;
    private int PropertyKeyType = 0;
    private int PropertyStatusCategory;
    private int TrustType;
    private boolean Flag;
    private String ErrorMsg;
    private String RunTime;

    public int getPropertyKeyType() {
        return PropertyKeyType;
    }

    public boolean getUserIsShowAddressDetail() {
        return UserIsShowAddressDetail;
    }

    public String getDetailAddressEnNoFoolrInfo() {
        return DetailAddressEnNoFoolrInfo;
    }

    public String getDetailAddressChNoFoolrInfo() {
        return DetailAddressChNoFoolrInfo;
    }

    public String getDetailAddressChInfo() {
        return DetailAddressChInfo;
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

    public boolean isODish() {
        return IsODish;
    }

    public String getPropertyProprietors() {
        return PropertyProprietors;
    }

    public String getDevelopmentEndCredits() {
        return DevelopmentEndCredits;
    }

    public boolean isConfirmed() {
        return IsConfirmed;
    }

    public int getSSDType() {
        return SSDType;
    }

    public String getSSDInfo() {
        return SSDInfo;
    }

    public boolean isFavorite() {
        return IsFavorite;
    }

    public boolean isHasOnlyTrust() {
        return HasOnlyTrust;
    }

    public void setOnlyTrust(boolean onlyTrust) {
        HasOnlyTrust = onlyTrust;
    }

    public String getHotList() {
        return HotList;
    }

    public String getPropertyCustomerNames() {
        return PropertyCustomerNames;
    }

    public String getPropertyBuildingOwner() {
        return PropertyBuildingOwner;
    }

    public String getSaleFloorPrice() {
        return SaleFloorPrice;
    }

    public String getOldSalePrice() {
        return OldSalePrice;
    }

    public String getRentFloorPrice() {
        return RentFloorPrice;
    }

    public int getTakeSeeCount() {
        return TakeSeeCount;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public String getSaleFloorPriceFormate() {
        return SaleFloorPriceFormate;
    }

    public String getRentFloorPriceFormate() {
        return RentFloorPriceFormate;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getSquareFoot() {
        return SquareFoot;
    }

    public String getSquareUseFoot() {
        return SquareUseFoot;
    }

    public String getSquareSource() {
        return SquareSource;
    }

    public String getSquareUseSource() {
        return SquareUseSource;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public String getRentPrice() {
        return RentPrice;
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

    public String getActualRentPriceUnit() {
        return ActualRentPriceUnit;
    }

    public String getPowerOfAttorneyThree() {
        return PowerOfAttorneyThree;
    }

    public String getPowerOfAttorneyFive() {
        return PowerOfAttorneyFive;
    }

    public String getEstimatedDate() {
        return EstimatedDate;
    }

    public String getProvideDate() {
        return ProvideDate;
    }

    public String getRegisterDate() {
        return RegisterDate;
    }

    public String getUtilityRatio() {
        return UtilityRatio;
    }

    public String getPrompt() {
        return Prompt;
    }

    public String getPropertyKeyNo() {
        return PropertyKeyNo;
    }

    public String getHouseDirection() {
        return HouseDirection;
    }

    public String getPropertyInterval() {
        return PropertyInterval;
    }

    public String getPropertyTags() {
        return PropertyTags;
    }

    public String getPropertyUsage() {
        return PropertyUsage;
    }

    public String getPropertyNote() {
        return PropertyNote;
    }

    public String getAllFloor() {
        return AllFloor;
    }

    public String getParkingNumber() {
        return ParkingNumber;
    }

    public String getDepartmentPermissions() {
        return DepartmentPermissions;
    }

    public String getPropertySource() {
        return PropertySource;
    }

    public String getSupply() {
        return Supply;
    }

    public String getCustomField1Name() {
        return CustomField1Name;
    }

    public String getCustomField1() {
        return CustomField1;
    }

    public String getCustomField2Name() {
        return CustomField2Name;
    }

    public String getCustomField2() {
        return CustomField2;
    }

    public String getCustomField3Name() {
        return CustomField3Name;
    }

    public String getCustomField3() {
        return CustomField3;
    }

    public String getRemark() {
        return Remark;
    }

    public String getSearchDate() {
        return SearchDate;
    }

    public String getAccessementNo() {
        return AccessementNo;
    }

    public String getCompleteYear() {
        return CompleteYear;
    }

    public String getMgrFee() {
        return MgrFee;
    }

    public String getPropertyChiefKeyId() {
        return PropertyChiefKeyId;
    }

    public String getPropertyChiefDepartmentKeyId() {
        return PropertyChiefDepartmentKeyId;
    }

    public String getPropertyTraderKeyId() {
        return PropertyTraderKeyId;
    }

    public String getPropertyTraderDepartmentKeyId() {
        return PropertyTraderDepartmentKeyId;
    }

    public int getPropertyStatusCategory() {
        return PropertyStatusCategory;
    }

    public int getTrustType() {
        return TrustType;
    }

    public boolean isFlag() {
        return Flag;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public String getRunTime() {
        return RunTime;
    }

    public void setUserIsShowAddressDetail(boolean userIsShowAddressDetail) {
        UserIsShowAddressDetail = userIsShowAddressDetail;
    }

    public void setDetailAddressEnNoFoolrInfo(String detailAddressEnNoFoolrInfo) {
        DetailAddressEnNoFoolrInfo = detailAddressEnNoFoolrInfo;
    }

    public void setDetailAddressChNoFoolrInfo(String detailAddressChNoFoolrInfo) {
        DetailAddressChNoFoolrInfo = detailAddressChNoFoolrInfo;
    }

    public void setDetailAddressChInfo(String detailAddressChInfo) {
        DetailAddressChInfo = detailAddressChInfo;
    }

    public void setHasOnlyTrust(boolean hasOnlyTrust) {
        HasOnlyTrust = hasOnlyTrust;
    }

    public void setSaleFloorPriceFormate(String saleFloorPriceFormate) {
        SaleFloorPriceFormate = saleFloorPriceFormate;
    }

    public void setRentFloorPriceFormate(String rentFloorPriceFormate) {
        RentFloorPriceFormate = rentFloorPriceFormate;
    }

    public void setPropertyKeyType(int propertyKeyType) {
        PropertyKeyType = propertyKeyType;
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

    public void setODish(boolean ODish) {
        IsODish = ODish;
    }

    public void setPropertyProprietors(String propertyProprietors) {
        PropertyProprietors = propertyProprietors;
    }

    public void setDevelopmentEndCredits(String developmentEndCredits) {
        DevelopmentEndCredits = developmentEndCredits;
    }

    public void setConfirmed(boolean confirmed) {
        IsConfirmed = confirmed;
    }

    public void setSSDType(int SSDType) {
        this.SSDType = SSDType;
    }

    public void setSSDInfo(String SSDInfo) {
        this.SSDInfo = SSDInfo;
    }

    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }

    public void setHotList(String hotList) {
        HotList = hotList;
    }

    public void setPropertyCustomerNames(String propertyCustomerNames) {
        PropertyCustomerNames = propertyCustomerNames;
    }

    public void setPropertyBuildingOwner(String propertyBuildingOwner) {
        PropertyBuildingOwner = propertyBuildingOwner;
    }

    public void setSaleFloorPrice(String saleFloorPrice) {
        SaleFloorPrice = saleFloorPrice;
    }

    public void setOldSalePrice(String oldSalePrice) {
        OldSalePrice = oldSalePrice;
    }

    public void setRentFloorPrice(String rentFloorPrice) {
        RentFloorPrice = rentFloorPrice;
    }

    public void setTakeSeeCount(int takeSeeCount) {
        TakeSeeCount = takeSeeCount;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public void setSquareFoot(String squareFoot) {
        SquareFoot = squareFoot;
    }

    public void setSquareUseFoot(String squareUseFoot) {
        SquareUseFoot = squareUseFoot;
    }

    public void setSquareSource(String squareSource) {
        SquareSource = squareSource;
    }

    public void setSquareUseSource(String squareUseSource) {
        SquareUseSource = squareUseSource;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public void setRentPrice(String rentPrice) {
        RentPrice = rentPrice;
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

    public void setActualRentPriceUnit(String actualRentPriceUnit) {
        ActualRentPriceUnit = actualRentPriceUnit;
    }

    public void setPowerOfAttorneyThree(String powerOfAttorneyThree) {
        PowerOfAttorneyThree = powerOfAttorneyThree;
    }

    public void setPowerOfAttorneyFive(String powerOfAttorneyFive) {
        PowerOfAttorneyFive = powerOfAttorneyFive;
    }

    public void setEstimatedDate(String estimatedDate) {
        EstimatedDate = estimatedDate;
    }

    public void setProvideDate(String provideDate) {
        ProvideDate = provideDate;
    }

    public void setRegisterDate(String registerDate) {
        RegisterDate = registerDate;
    }

    public void setUtilityRatio(String utilityRatio) {
        UtilityRatio = utilityRatio;
    }

    public void setPrompt(String prompt) {
        Prompt = prompt;
    }

    public void setPropertyKeyNo(String propertyKeyNo) {
        PropertyKeyNo = propertyKeyNo;
    }

    public void setHouseDirection(String houseDirection) {
        HouseDirection = houseDirection;
    }

    public void setPropertyInterval(String propertyInterval) {
        PropertyInterval = propertyInterval;
    }


    public void setPropertyTags(String propertyTags) {
        PropertyTags = propertyTags;
    }

    public void setPropertyUsage(String propertyUsage) {
        PropertyUsage = propertyUsage;
    }

    public void setPropertyNote(String propertyNote) {
        PropertyNote = propertyNote;
    }

    public void setAllFloor(String allFloor) {
        AllFloor = allFloor;
    }

    public void setParkingNumber(String parkingNumber) {
        ParkingNumber = parkingNumber;
    }

    public void setDepartmentPermissions(String departmentPermissions) {
        DepartmentPermissions = departmentPermissions;
    }

    public void setPropertySource(String propertySource) {
        PropertySource = propertySource;
    }

    public void setSupply(String supply) {
        Supply = supply;
    }

    public void setCustomField1Name(String customField1Name) {
        CustomField1Name = customField1Name;
    }

    public void setCustomField1(String customField1) {
        CustomField1 = customField1;
    }

    public void setCustomField2Name(String customField2Name) {
        CustomField2Name = customField2Name;
    }

    public void setCustomField2(String customField2) {
        CustomField2 = customField2;
    }

    public void setCustomField3Name(String customField3Name) {
        CustomField3Name = customField3Name;
    }

    public void setCustomField3(String customField3) {
        CustomField3 = customField3;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public void setSearchDate(String searchDate) {
        SearchDate = searchDate;
    }

    public void setAccessementNo(String accessementNo) {
        AccessementNo = accessementNo;
    }

    public void setCompleteYear(String completeYear) {
        CompleteYear = completeYear;
    }

    public void setMgrFee(String mgrFee) {
        MgrFee = mgrFee;
    }

    public void setPropertyChiefKeyId(String propertyChiefKeyId) {
        PropertyChiefKeyId = propertyChiefKeyId;
    }

    public void setPropertyChiefDepartmentKeyId(String propertyChiefDepartmentKeyId) {
        PropertyChiefDepartmentKeyId = propertyChiefDepartmentKeyId;
    }

    public void setPropertyTraderKeyId(String propertyTraderKeyId) {
        PropertyTraderKeyId = propertyTraderKeyId;
    }

    public void setPropertyTraderDepartmentKeyId(String propertyTraderDepartmentKeyId) {
        PropertyTraderDepartmentKeyId = propertyTraderDepartmentKeyId;
    }

    public void setPropertyStatusCategory(int propertyStatusCategory) {
        PropertyStatusCategory = propertyStatusCategory;
    }

    public void setTrustType(int trustType) {
        TrustType = trustType;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    @Override
    public String toString() {
        return "DetailHouseData{" +
                "UserIsShowAddressDetail='" + UserIsShowAddressDetail + '\'' +
                ", DetailAddressEnNoFoolrInfo='" + DetailAddressEnNoFoolrInfo + '\'' +
                ", DetailAddressChNoFoolrInfo='" + DetailAddressChNoFoolrInfo + '\'' +
                ", DetailAddressChInfo='" + DetailAddressChInfo + '\'' +
                ", PropertyNo='" + PropertyNo + '\'' +
                ", PropertyStatus='" + PropertyStatus + '\'' +
                ", PropertyStatusCode='" + PropertyStatusCode + '\'' +
                ", IsODish=" + IsODish +
                ", PropertyProprietors='" + PropertyProprietors + '\'' +
                ", DevelopmentEndCredits='" + DevelopmentEndCredits + '\'' +
                ", IsConfirmed=" + IsConfirmed +
                ", SSDType=" + SSDType +
                ", SSDInfo='" + SSDInfo + '\'' +
                ", IsFavorite=" + IsFavorite +
                ", HotList='" + HotList + '\'' +
                ", PropertyCustomerNames='" + PropertyCustomerNames + '\'' +
                ", PropertyBuildingOwner='" + PropertyBuildingOwner + '\'' +
                ", SaleFloorPrice='" + SaleFloorPrice + '\'' +
                ", OldSalePrice='" + OldSalePrice + '\'' +
                ", RentFloorPrice='" + RentFloorPrice + '\'' +
                ", TakeSeeCount=" + TakeSeeCount +
                ", DistrictName='" + DistrictName + '\'' +
                ", AreaName='" + AreaName + '\'' +
                ", SquareFoot='" + SquareFoot + '\'' +
                ", SquareUseFoot='" + SquareUseFoot + '\'' +
                ", SquareSource='" + SquareSource + '\'' +
                ", SquareUseSource='" + SquareUseSource + '\'' +
                ", SalePrice='" + SalePrice + '\'' +
                ", RentPrice='" + RentPrice + '\'' +
                ", SalePriceUnit='" + SalePriceUnit + '\'' +
                ", ActualSalePriceUnit='" + ActualSalePriceUnit + '\'' +
                ", RentPriceUnit='" + RentPriceUnit + '\'' +
                ", ActualRentPriceUnit='" + ActualRentPriceUnit + '\'' +
                ", PowerOfAttorneyThree='" + PowerOfAttorneyThree + '\'' +
                ", PowerOfAttorneyFive='" + PowerOfAttorneyFive + '\'' +
                ", EstimatedDate='" + EstimatedDate + '\'' +
                ", ProvideDate='" + ProvideDate + '\'' +
                ", RegisterDate='" + RegisterDate + '\'' +
                ", UtilityRatio='" + UtilityRatio + '\'' +
                ", Prompt='" + Prompt + '\'' +
                ", PropertyKeyNo='" + PropertyKeyNo + '\'' +
                ", HouseDirection='" + HouseDirection + '\'' +
                ", PropertyInterval='" + PropertyInterval + '\'' +
                ", PropertyTags='" + PropertyTags + '\'' +
                ", PropertyUsage='" + PropertyUsage + '\'' +
                ", PropertyNote='" + PropertyNote + '\'' +
                ", AllFloor='" + AllFloor + '\'' +
                ", ParkingNumber='" + ParkingNumber + '\'' +
                ", DepartmentPermissions='" + DepartmentPermissions + '\'' +
                ", PropertySource='" + PropertySource + '\'' +
                ", Supply='" + Supply + '\'' +
                ", CustomField1Name='" + CustomField1Name + '\'' +
                ", CustomField1='" + CustomField1 + '\'' +
                ", CustomField2Name='" + CustomField2Name + '\'' +
                ", CustomField2='" + CustomField2 + '\'' +
                ", CustomField3Name='" + CustomField3Name + '\'' +
                ", CustomField3='" + CustomField3 + '\'' +
                ", Remark='" + Remark + '\'' +
                ", SearchDate='" + SearchDate + '\'' +
                ", AccessementNo='" + AccessementNo + '\'' +
                ", CompleteYear='" + CompleteYear + '\'' +
                ", MgrFee='" + MgrFee + '\'' +
                ", PropertyChiefKeyId='" + PropertyChiefKeyId + '\'' +
                ", PropertyChiefDepartmentKeyId='" + PropertyChiefDepartmentKeyId + '\'' +
                ", PropertyTraderKeyId='" + PropertyTraderKeyId + '\'' +
                ", PropertyTraderDepartmentKeyId='" + PropertyTraderDepartmentKeyId + '\'' +
                ", PropertyStatusCategory=" + PropertyStatusCategory +
                ", TrustType=" + TrustType +
                ", Flag=" + Flag +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
