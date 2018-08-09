package com.centanet.hk.aplus.bean.detail;

import com.centanet.hk.aplus.bean.BaseResponse;

/**
 * Created by yangzm4 on 2018/3/8.
 */

public class DetailAddressResponse extends BaseResponse{
    private String DetailAddressChInfo;
    private String DetailAddressEnInfo;

    public void setDetailAddressChInfo(String detailAddressChInfo) {
        DetailAddressChInfo = detailAddressChInfo;
    }


    public String getDetailAddressChInfo() {
        return DetailAddressChInfo;
    }


    public void setDetailAddressEnInfo(String detailAddressEnInfo) {
        DetailAddressEnInfo = detailAddressEnInfo;
    }

    public String getDetailAddressEnInfo() {
        return DetailAddressEnInfo;
    }

    @Override
    public String toString() {
        return "DetailAddress{" +
                "DetailAddressChInfo='" + DetailAddressChInfo + '\'' +
                ", DetailAddressEnInfo='" + DetailAddressEnInfo + '\'' +
                '}';
    }
}
