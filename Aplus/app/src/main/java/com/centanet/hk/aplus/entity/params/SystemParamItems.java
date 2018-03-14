package com.centanet.hk.aplus.entity.params;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class SystemParamItems implements Serializable{

    String ItemValue;
    String ItemText;
    String ItemFullText;
    String ItemCode;
    String ItemStatus;
    String ExtendAttr;
    String FlagDefault;
    String Seq;
    String IsShow;
    List<SysParameterTagItems> SysParameterTagItems;

    public SystemParamItems() {
    }

    public SystemParamItems(String itemValue, String itemText, String itemFullText, String itemCode, String itemStatus, String extendAttr, String flagDefault, String seq, String isShow, List<com.centanet.hk.aplus.entity.params.SysParameterTagItems> sysParameterTagItems) {
        ItemValue = itemValue;
        ItemText = itemText;
        ItemFullText = itemFullText;
        ItemCode = itemCode;
        ItemStatus = itemStatus;
        ExtendAttr = extendAttr;
        FlagDefault = flagDefault;
        Seq = seq;
        IsShow = isShow;
        SysParameterTagItems = sysParameterTagItems;
    }

    public void setItemValue(String itemValue) {
        ItemValue = itemValue;
    }

    public void setItemText(String itemText) {
        ItemText = itemText;
    }

    public void setItemFullText(String itemFullText) {
        ItemFullText = itemFullText;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public void setItemStatus(String itemStatus) {
        ItemStatus = itemStatus;
    }

    public void setExtendAttr(String extendAttr) {
        ExtendAttr = extendAttr;
    }

    public void setFlagDefault(String flagDefault) {
        FlagDefault = flagDefault;
    }

    public void setSeq(String seq) {
        Seq = seq;
    }

    public void setIsShow(String isShow) {
        IsShow = isShow;
    }

    public void setSysParameterTagItems(List<com.centanet.hk.aplus.entity.params.SysParameterTagItems> sysParameterTagItems) {
        SysParameterTagItems = sysParameterTagItems;
    }

    public String getItemValue() {
        return ItemValue;
    }

    public String getItemText() {
        return ItemText;
    }

    public String getItemFullText() {
        return ItemFullText;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public String getExtendAttr() {
        return ExtendAttr;
    }

    public String getFlagDefault() {
        return FlagDefault;
    }

    public String getSeq() {
        return Seq;
    }

    public String getIsShow() {
        return IsShow;
    }

    public List<com.centanet.hk.aplus.entity.params.SysParameterTagItems> getSysParameterTagItems() {
        return SysParameterTagItems;
    }

    @Override
    public String toString() {
        return "SystemParamItems{" +
                "ItemValue='" + ItemValue + '\'' +
                ", ItemText='" + ItemText + '\'' +
                ", ItemFullText='" + ItemFullText + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", ItemStatus='" + ItemStatus + '\'' +
                ", ExtendAttr='" + ExtendAttr + '\'' +
                ", FlagDefault='" + FlagDefault + '\'' +
                ", Seq='" + Seq + '\'' +
                ", IsShow='" + IsShow + '\'' +
                ", SysParameterTagItems=" + SysParameterTagItems +
                '}';
    }
}
