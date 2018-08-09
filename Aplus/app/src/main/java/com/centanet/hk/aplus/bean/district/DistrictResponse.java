package com.centanet.hk.aplus.bean.district;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/20.
 */

public class DistrictResponse extends BaseResponse{

    private List<DistrictItem> DistrictItems;

    public DistrictResponse() {
    }

    public DistrictResponse(List<DistrictItem> districtItems, boolean flag, String errorMsg, String runTime) {
        super(flag,errorMsg,runTime);
        DistrictItems = districtItems;
    }

    public void setDistrictItems(List<DistrictItem> districtItems) {
        DistrictItems = districtItems;
    }


    public List<DistrictItem> getDistrictItems() {
        return DistrictItems;
    }


    @Override
    public String toString() {
        return "DistrictResponse{" +
                "DistrictItems=" + DistrictItems +
                '}';
    }
}
