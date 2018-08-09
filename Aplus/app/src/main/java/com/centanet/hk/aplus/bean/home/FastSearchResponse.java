package com.centanet.hk.aplus.bean.home;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public class FastSearchResponse extends BaseResponse{

    private List<PropertyFastSearcherTag> PropertyFastSearcherTags;

    public FastSearchResponse() {
    }

    public FastSearchResponse(List<PropertyFastSearcherTag> propertyFastSearcherTags, boolean flag, String errorMsg, String runTime) {
        super(flag,errorMsg,runTime);
        PropertyFastSearcherTags = propertyFastSearcherTags;
    }

    public List<PropertyFastSearcherTag> getPropertyFastSearcherTags() {
        return PropertyFastSearcherTags;
    }

    public void setPropertyFastSearcherTags(List<PropertyFastSearcherTag> propertyFastSearcherTags) {
        PropertyFastSearcherTags = propertyFastSearcherTags;
    }

    @Override
    public String toString() {
        return "FastSearchResponse{" +
                "PropertyFastSearcherTags=" + PropertyFastSearcherTags +
                '}';
    }
}
