package com.centanet.hk.aplus.bean.auto_estate;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/1.
 */

public class AutoHouseResponse extends BaseResponse{
    private List<PropertyParamHints> PropertyParamHints;

    public void setPropertyParamHints(List<PropertyParamHints> propertyParamHints) {
        PropertyParamHints = propertyParamHints;
    }

    public List<com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints> getPropertyParamHints() {
        return PropertyParamHints;
    }


    @Override
    public String toString() {
        return "AutoHouseData{" +
                "PropertyParamHints=" + PropertyParamHints +
                '}';
    }
}
