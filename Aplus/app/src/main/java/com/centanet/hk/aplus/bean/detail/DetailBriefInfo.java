package com.centanet.hk.aplus.bean.detail;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/2.
 */

public class DetailBriefInfo {

    private List<PropertyFollow> PropertyFollows;           /** 房源跟進集合信息 */
    private List<PropertyTransaction> PropertyTransactions; /**成交集合信息 */
    private String PropertyTrustorsErrorMsg;                /**業主錯誤信息 */
    private DetailTrustor PropertyTrustors;           /** 業主信息列表 */
    private DetailDataInformation CentaData;
    private boolean Flag;
    private List<PriceTrust> RentTrusts;
    private List<PriceTrust> SaleTrusts;
    private String ErrorMsg;
    private String RunTime;

    public void setPropertyFollows(List<PropertyFollow> propertyFollows) {
        PropertyFollows = propertyFollows;
    }

    public void setPropertyTransactions(List<PropertyTransaction> propertyTransactions) {
        PropertyTransactions = propertyTransactions;
    }

    public void setPropertyTrustorsErrorMsg(String propertyTrustorsErrorMsg) {
        PropertyTrustorsErrorMsg = propertyTrustorsErrorMsg;
    }

    public void setCentaData(DetailDataInformation centaData) {
        CentaData = centaData;
    }

    public void setPropertyTrustors(DetailTrustor propertyTrustors) {
        PropertyTrustors = propertyTrustors;
    }

    public void setRentTrusts(List<PriceTrust> rentTrusts) {
        RentTrusts = rentTrusts;
    }

    public void setSaleTrusts(List<PriceTrust> saleTrusts) {
        SaleTrusts = saleTrusts;
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

    public List<PropertyFollow> getPropertyFollows() {
        return PropertyFollows;
    }

    public List<PropertyTransaction> getPropertyTransactions() {
        return PropertyTransactions;
    }

    public String getPropertyTrustorsErrorMsg() {
        return PropertyTrustorsErrorMsg;
    }

    public DetailDataInformation getCentaData() {
        return CentaData;
    }

    public DetailTrustor getPropertyTrustors() {
        return PropertyTrustors;
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

    public List<PriceTrust> getRentTrusts() {
        return RentTrusts;
    }

    public List<PriceTrust> getSaleTrusts() {
        return SaleTrusts;
    }

    @Override
    public String toString() {
        return "DetailBriefInfo{" +
                "PropertyFollows=" + PropertyFollows +
                ", PropertyTransactions=" + PropertyTransactions +
                ", PropertyTrustorsErrorMsg='" + PropertyTrustorsErrorMsg + '\'' +
                ", PropertyTrustors=" + PropertyTrustors +
                ", CentaData=" + CentaData +
                ", Flag=" + Flag +
                ", RentTrusts=" + RentTrusts +
                ", SaleTrusts=" + SaleTrusts +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
