package com.centanet.hk.aplus.bean.detail;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/7/2.
 */

public class PropertyTransaction implements Serializable{
    private String DetailAddressChInfo;         /** 詳細中文地址 - {城區} {片區} {樓盤} {棟座} {樓層}樓 {單位}室  */
    private String DetailAddressEnInfo;         /** 詳細英文地址 - Flat {單位}, Floor {樓層}, {En棟座}, {En樓盤}  */
    private String TransactionLogKeyId;         /** 成交KeyId */
    private String CompleteDate;                /** 完成日期 */
    private String FormalDate;                  /** 正式時間 */
    private String PrelimDate;                  /** 臨時時間 */
    private String PropertyKeyId;               /** 房源KeyId */
    private String CreateUserName;              /** 建立人 */
    private String CreateUserKeyId;             /** 建立人KeyId */
    private String CreateTime;                  /** 建立時間 */
    private String UpdateUserName;              /** 修改人 */
    private String UpdateUserKeyId;             /** 修改人KeyId */
    private String UpdateTime;                  /** 修改時間 */
    private int Status;                      /** 類別 选项：1--Y(中原售)，2--YT(中原租)，3--X(行家售)，4--XT(行家租)，5--內部轉讓 */
    private String Agency;                      /** 行家 */
    private String Price;                       /** 成交價格 */
    private boolean IsTranferToConfirm;         /** 是否從未確認成交轉成確認成交 */
    private String PriceFoot;
    private String UseSquare;                   /** 实用面積 */
    private String UseSquareFoot;               /** 实用面積（尺） */
    private String BuildSquare;                 /** 建築面積 */
    private String BuildSquareFoot;             /** 建築面積（尺） */
    private String AveragePrice;                /** 實均 */
    private String GrossAveragePrice;           /** 建均 */
    private String TransactionDate;             /** 成交日期 */
    private String RentEndDate;                 /** 租期至 租约至 */
    private boolean IsTransferred;              /** 以確定人身份轉讓 */
    private boolean IsNotConfirmed;             /** 是否確定成交 */
    private boolean IsDevelopmentEndCredits;    /** 發展商貨尾 */
    private boolean IsNoContact;                /** 業主不可聯繫 */
    private boolean IsCorporationTransferred;   /** 公司轉讓 */
    private String Agent;                       /** 成交經紀人 */
    private String TransactionNo;               /** 租期至 租约至 */
    private String MemorialNo;                  /** 成交編號 */
    private String Remark;                      /** 備註 */
    private String PropertyHistoriesKeyId;      /** 歷史成交Id */
    private String DisplayTransDate;            /** 租期至 租约至 */
    private boolean IsGreen;                    /** 是否是綠表價 */
    private String KeyId;                       /** 對象keyid */

    public void setDetailAddressChInfo(String detailAddressChInfo) {
        DetailAddressChInfo = detailAddressChInfo;
    }

    public void setDetailAddressEnInfo(String detailAddressEnInfo) {
        DetailAddressEnInfo = detailAddressEnInfo;
    }

    public void setTransactionLogKeyId(String transactionLogKeyId) {
        TransactionLogKeyId = transactionLogKeyId;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }

    public void setFormalDate(String formalDate) {
        FormalDate = formalDate;
    }

    public void setPrelimDate(String prelimDate) {
        PrelimDate = prelimDate;
    }

    public void setPropertyKeyId(String propertyKeyId) {
        PropertyKeyId = propertyKeyId;
    }

    public void setCreateUserName(String createUserName) {
        CreateUserName = createUserName;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setUpdateUserName(String updateUserName) {
        UpdateUserName = updateUserName;
    }

    public void setUpdateUserKeyId(String updateUserKeyId) {
        UpdateUserKeyId = updateUserKeyId;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setAgency(String agency) {
        Agency = agency;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setTranferToConfirm(boolean tranferToConfirm) {
        IsTranferToConfirm = tranferToConfirm;
    }

    public void setPriceFoot(String priceFoot) {
        PriceFoot = priceFoot;
    }

    public void setUseSquare(String useSquare) {
        UseSquare = useSquare;
    }

    public void setUseSquareFoot(String useSquareFoot) {
        UseSquareFoot = useSquareFoot;
    }

    public void setBuildSquareFoot(String buildSquareFoot) {
        BuildSquareFoot = buildSquareFoot;
    }

    public void setAveragePrice(String averagePrice) {
        AveragePrice = averagePrice;
    }

    public void setGrossAveragePrice(String grossAveragePrice) {
        GrossAveragePrice = grossAveragePrice;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public void setRentEndDate(String rentEndDate) {
        RentEndDate = rentEndDate;
    }

    public void setTransferred(boolean transferred) {
        IsTransferred = transferred;
    }

    public void setNotConfirmed(boolean notConfirmed) {
        IsNotConfirmed = notConfirmed;
    }

    public void setNoContact(boolean noContact) {
        IsNoContact = noContact;
    }

    public void setCorporationTransferred(boolean corporationTransferred) {
        IsCorporationTransferred = corporationTransferred;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public void setTransactionNo(String transactionNo) {
        TransactionNo = transactionNo;
    }

    public void setMemorialNo(String memorialNo) {
        MemorialNo = memorialNo;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public void setPropertyHistoriesKeyId(String propertyHistoriesKeyId) {
        PropertyHistoriesKeyId = propertyHistoriesKeyId;
    }

    public void setDisplayTransDate(String displayTransDate) {
        DisplayTransDate = displayTransDate;
    }

    public void setGreen(boolean green) {
        IsGreen = green;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getDetailAddressChInfo() {
        return DetailAddressChInfo;
    }

    public String getDetailAddressEnInfo() {
        return DetailAddressEnInfo;
    }

    public String getTransactionLogKeyId() {
        return TransactionLogKeyId;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public String getFormalDate() {
        return FormalDate;
    }

    public String getPrelimDate() {
        return PrelimDate;
    }

    public String getPropertyKeyId() {
        return PropertyKeyId;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getUpdateUserName() {
        return UpdateUserName;
    }

    public String getUpdateUserKeyId() {
        return UpdateUserKeyId;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public int getStatus() {
        return Status;
    }

    public String getAgency() {
        return Agency;
    }

    public String getPrice() {
        return Price;
    }

    public boolean isTranferToConfirm() {
        return IsTranferToConfirm;
    }

    public String getPriceFoot() {
        return PriceFoot;
    }

    public String getUseSquare() {
        return UseSquare;
    }

    public String getUseSquareFoot() {
        return UseSquareFoot;
    }

    public String getBuildSquareFoot() {
        return BuildSquareFoot;
    }

    public String getAveragePrice() {
        return AveragePrice;
    }

    public String getGrossAveragePrice() {
        return GrossAveragePrice;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public String getRentEndDate() {
        return RentEndDate;
    }

    public boolean isTransferred() {
        return IsTransferred;
    }

    public boolean isNotConfirmed() {
        return IsNotConfirmed;
    }

    public boolean isNoContact() {
        return IsNoContact;
    }

    public boolean isCorporationTransferred() {
        return IsCorporationTransferred;
    }

    public String getAgent() {
        return Agent;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getMemorialNo() {
        return MemorialNo;
    }

    public String getRemark() {
        return Remark;
    }

    public String getPropertyHistoriesKeyId() {
        return PropertyHistoriesKeyId;
    }

    public String getDisplayTransDate() {
        return DisplayTransDate;
    }

    public boolean isGreen() {
        return IsGreen;
    }

    public String getKeyId() {
        return KeyId;
    }

    @Override
    public String toString() {
        return "PropertyTransaction{" +
                "DetailAddressChInfo='" + DetailAddressChInfo + '\'' +
                ", DetailAddressEnInfo='" + DetailAddressEnInfo + '\'' +
                ", TransactionLogKeyId='" + TransactionLogKeyId + '\'' +
                ", CompleteDate='" + CompleteDate + '\'' +
                ", FormalDate='" + FormalDate + '\'' +
                ", PrelimDate='" + PrelimDate + '\'' +
                ", PropertyKeyId='" + PropertyKeyId + '\'' +
                ", CreateUserName='" + CreateUserName + '\'' +
                ", CreateUserKeyId='" + CreateUserKeyId + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", UpdateUserName='" + UpdateUserName + '\'' +
                ", UpdateUserKeyId='" + UpdateUserKeyId + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                ", Status='" + Status + '\'' +
                ", Agency='" + Agency + '\'' +
                ", Price='" + Price + '\'' +
                ", IsTranferToConfirm=" + IsTranferToConfirm +
                ", PriceFoot='" + PriceFoot + '\'' +
                ", UseSquare='" + UseSquare + '\'' +
                ", UseSquareFoot='" + UseSquareFoot + '\'' +
                ", BuildSquare='" + BuildSquare + '\'' +
                ", BuildSquareFoot='" + BuildSquareFoot + '\'' +
                ", AveragePrice='" + AveragePrice + '\'' +
                ", GrossAveragePrice='" + GrossAveragePrice + '\'' +
                ", TransactionDate='" + TransactionDate + '\'' +
                ", RentEndDate='" + RentEndDate + '\'' +
                ", IsTransferred=" + IsTransferred +
                ", IsNotConfirmed=" + IsNotConfirmed +
                ", IsDevelopmentEndCredits=" + IsDevelopmentEndCredits +
                ", IsNoContact=" + IsNoContact +
                ", IsCorporationTransferred=" + IsCorporationTransferred +
                ", Agent='" + Agent + '\'' +
                ", TransactionNo='" + TransactionNo + '\'' +
                ", MemorialNo='" + MemorialNo + '\'' +
                ", Remark='" + Remark + '\'' +
                ", PropertyHistoriesKeyId='" + PropertyHistoriesKeyId + '\'' +
                ", DisplayTransDate='" + DisplayTransDate + '\'' +
                ", IsGreen=" + IsGreen +
                ", KeyId='" + KeyId + '\'' +
                '}';
    }
}
