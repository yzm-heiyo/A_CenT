package com.centanet.hk.aplus.bean.district;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

/**
 * Created by yangzm4 on 2018/7/20.
 */

public class DistrictItem extends LitePalSupport{

    private String DistrictName;
    private String DistrictKeyId;

    public DistrictItem() {
    }

    public DistrictItem(String districtName, String districtKeyId) {
        DistrictName = districtName;
        DistrictKeyId = districtKeyId;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public void setDistrictKeyId(String districtKeyId) {
        DistrictKeyId = districtKeyId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public String getDistrictKeyId() {
        return DistrictKeyId;
    }

    @Override
    public String toString() {
        return "DistrictItem{" +
                "DistrictName='" + DistrictName + '\'' +
                ", DistrictKeyId='" + DistrictKeyId + '\'' +
                '}';
    }
}
