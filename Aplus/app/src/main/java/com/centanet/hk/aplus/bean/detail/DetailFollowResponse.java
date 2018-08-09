package com.centanet.hk.aplus.bean.detail;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/9.
 */

public class DetailFollowResponse extends BaseResponse{

    private List<PropertyFollow> PropertyFollows;

    public void setPropertyFollows(List<PropertyFollow> propertyFollows) {
        PropertyFollows = propertyFollows;
    }


    public List<PropertyFollow> getPropertyFollows() {
        return PropertyFollows;
    }


    @Override
    public String toString() {
        return "DetailFollowData{" +
                "PropertyFollows=" + PropertyFollows +
                '}';
    }
}
