package com.centanet.hk.aplus.entity.complexSearch;

import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangzm4 on 2018/3/26.
 */

public class Operation implements Serializable{

    private Date completeDateStart, completeDateEnd, changeDateStart, changeDateEnd;
    private int areaType = 1, priceType = 1;
    private String sreaTxt, priceTxt;
    private String owner;
    private String phone;
    private String areaFrom;
    private String areaTo;
    private String priceFrom;
    private String priceTo;
    private int dateType;
    private String ssdType;
    private String dateTypeText;
    private String completeStartText;
    private String completeEndText;
    private String dateStartText;
    private String dateEndText;
    private String staBeginText;
    private String staEndText;
    private List<Integer> staBeginSelectList, staEndSelectList;
    private List<String> staValueEnd;
    private List<String> staValueStart;
    private List<String> intervalList;
    private List<String> directionList;
    private List<String> houseTagList;
    private int ssdSelectId;

    public void setCompleteDateStart(Date completeDateStart) {
        this.completeDateStart = completeDateStart;
    }

    public void setCompleteDateEnd(Date completeDateEnd) {
        this.completeDateEnd = completeDateEnd;
    }

    public void setChangeDateStart(Date changeDateStart) {
        this.changeDateStart = changeDateStart;
    }

    public void setChangeDateEnd(Date changeDateEnd) {
        this.changeDateEnd = changeDateEnd;
    }

    public void setHouseTagList(List<String> houseTagList) {
        this.houseTagList = houseTagList;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public void setCompleteStartText(String completeStartText) {
        this.completeStartText = completeStartText;
    }

    public void setCompleteEndText(String completeEndText) {
        this.completeEndText = completeEndText;
    }


    public void setDateStartText(String dateStartText) {
        this.dateStartText = dateStartText;
    }

    public void setDateEndText(String dateEndText) {
        this.dateEndText = dateEndText;
    }

    public void setSsdSelectId(int ssdSelectId) {
        this.ssdSelectId = ssdSelectId;
    }

    public void setStaValueEnd(List<String> staValueEnd) {
        this.staValueEnd = staValueEnd;
    }

    public void setStaValueStart(List<String> staValueStart) {
        this.staValueStart = staValueStart;
    }

    public List<String> getStaValueEnd() {
        return staValueEnd;
    }

    public List<String> getStaValueStart() {
        return staValueStart;
    }

    public int getSsdSelectId() {
        return ssdSelectId;
    }

    public String getCompleteStartText() {
        return completeStartText;
    }

    public String getCompleteEndText() {
        return completeEndText;
    }

    public String getDateStartText() {
        return dateStartText;
    }

    public String getDateEndText() {
        return dateEndText;
    }

    public String getDateTypeText() {
        return dateTypeText;
    }

    public void setDateTypeText(String dateTypeText) {
        this.dateTypeText = dateTypeText;
    }

    public void setSreaTxt(String sreaTxt) {
        this.sreaTxt = sreaTxt;
    }

    public void setPriceTxt(String priceTxt) {
        this.priceTxt = priceTxt;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public void setSsdType(String ssdType) {
        this.ssdType = ssdType;
    }

    public void setIntervalList(List<String> intervalList) {
        this.intervalList = intervalList;
    }

    public void setDirectionList(List<String> directionList) {
        this.directionList = directionList;
    }

    public void setAreaFrom(String areaFrom) {
        this.areaFrom = areaFrom;
    }

    public void setAreaTo(String areaTo) {
        this.areaTo = areaTo;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }

    public void setStaBeginSelectList(List<Integer> staBeginSelectList) {
        this.staBeginSelectList = staBeginSelectList;
    }

    public void setStaEndSelectList(List<Integer> staEndSelectList) {
        this.staEndSelectList = staEndSelectList;
    }

    public void setStaBeginText(String staBeginText) {
        this.staBeginText = staBeginText;
    }

    public void setStaEndText(String staEndText) {
        this.staEndText = staEndText;
    }

    public String getStaBeginText() {
        return staBeginText;
    }

    public String getStaEndText() {
        return staEndText;
    }

    public List<Integer> getStaBeginSelectList() {
        return staBeginSelectList;
    }

    public List<Integer> getStaEndSelectList() {
        return staEndSelectList;
    }

    public String getAreaFrom() {
        return areaFrom;
    }

    public String getAreaTo() {
        return areaTo;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public int getAreaType() {
        return areaType;
    }

    public int getPriceType() {
        return priceType;
    }

    public String getSreaTxt() {
        return sreaTxt;
    }

    public String getPriceTxt() {
        return priceTxt;
    }

    public String getOwner() {
        return owner;
    }

    public String getPhone() {
        return phone;
    }

    public int getDateType() {
        return dateType;
    }

    public String getSsdType() {
        return ssdType;
    }

    public List<String> getIntervalList() {
        return intervalList;
    }

    public List<String> getDirectionList() {
        return directionList;
    }

    public List<String> getHouseTagList() {
        return houseTagList;
    }

    public Date getCompleteDateStart() {
        return completeDateStart;
    }

    public Date getCompleteDateEnd() {
        return completeDateEnd;
    }

    public Date getChangeDateStart() {
        return changeDateStart;
    }

    public Date getChangeDateEnd() {
        return changeDateEnd;
    }
}
