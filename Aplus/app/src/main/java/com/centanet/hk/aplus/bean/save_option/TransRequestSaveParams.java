package com.centanet.hk.aplus.bean.save_option;

import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/8/8.
 */

public class TransRequestSaveParams {

    private List<SystemParamItems> statulist = new ArrayList<>();
    private List<SystemParamItems> tagList = new ArrayList<>();
    private List<SystemParamItems> directionList = new ArrayList<>();

    private List<SystemParamItems> intervalList = new ArrayList<>();

    private List<PropertyParamHints> address = new ArrayList<>();

    private List<TagInfo> tagInfos = new ArrayList<>();

    private List<DistrictItem> area = new ArrayList<>();

    private List<SystemParamItems> statuFrom = new ArrayList<>();

    private List<SystemParamItems> statuTo = new ArrayList<>();

    public TransRequestSaveParams() {
    }

    public TransRequestSaveParams(List<SystemParamItems> statulist, List<SystemParamItems> tagList, List<SystemParamItems> directionList, List<SystemParamItems> intervalList, List<PropertyParamHints> address, List<TagInfo> tagInfos, List<DistrictItem> area, List<SystemParamItems> statuFrom, List<SystemParamItems> statuTo) {
        this.statulist = statulist;
        this.tagList = tagList;
        this.directionList = directionList;
        this.intervalList = intervalList;
        this.address = address;
        this.tagInfos = tagInfos;
        this.area = area;
        this.statuFrom = statuFrom;
        this.statuTo = statuTo;
    }

    public void setStatulist(List<SystemParamItems> statulist) {
        this.statulist = statulist;
    }

    public void setTagList(List<SystemParamItems> tagList) {
        this.tagList = tagList;
    }

    public void setDirectionList(List<SystemParamItems> directionList) {
        this.directionList = directionList;
    }

    public void setIntervalList(List<SystemParamItems> intervalList) {
        this.intervalList = intervalList;
    }

    public void setTagInfos(List<TagInfo> tagInfos) {
        this.tagInfos = tagInfos;
    }

    public void setArea(List<DistrictItem> area) {
        this.area = area;
    }

    public void setAddress(List<PropertyParamHints> address) {
        this.address = address;
    }

    public void setStatuFrom(List<SystemParamItems> statuFrom) {
        this.statuFrom = statuFrom;
    }

    public void setStatuTo(List<SystemParamItems> statuTo) {
        this.statuTo = statuTo;
    }

    public List<SystemParamItems> getStatuFrom() {
        return statuFrom;
    }

    public List<SystemParamItems> getStatuTo() {
        return statuTo;
    }

    public List<DistrictItem> getArea() {
        return area;
    }

    public List<PropertyParamHints> getAddress() {
        return address;
    }

    public List<SystemParamItems> getStatulist() {
        return statulist;
    }

    public List<SystemParamItems> getTagList() {
        return tagList;
    }

    public List<SystemParamItems> getDirectionList() {
        return directionList;
    }

    public List<SystemParamItems> getIntervalList() {
        return intervalList;
    }

    public List<TagInfo> getTagInfos() {
        return tagInfos;
    }

    @Override
    public String toString() {
        return "PropertyRequestSaveParams{" +
                "statulist=" + statulist +
                ", tagList=" + tagList +
                ", directionList=" + directionList +
                ", intervalList=" + intervalList +
                ", address=" + address +
                ", tagInfos=" + tagInfos +
                ", area=" + area +
                ", statuFrom=" + statuFrom +
                ", statuTo=" + statuTo +
                '}';
    }
}
