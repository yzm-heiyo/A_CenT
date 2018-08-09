package com.centanet.hk.aplus.bean.detail;

import com.centanet.hk.aplus.bean.BaseResponse;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/16.
 */

public class DetaileNextKeyIdResponse extends BaseResponse{

    private List<String> KeyIds;

    public DetaileNextKeyIdResponse() {
    }

    public DetaileNextKeyIdResponse(List<String> keyIds) {
        KeyIds = keyIds;
    }

    public DetaileNextKeyIdResponse(boolean flag, String errorMsg, String runTime, List<String> keyIds) {
        super(flag, errorMsg, runTime);
        KeyIds = keyIds;
    }

    public void setKeyIds(List<String> keyIds) {
        KeyIds = keyIds;
    }


    public List<String> getKeyIds() {
        return KeyIds;
    }


    @Override
    public String toString() {
        return "DetaileNextKeyIdResponse{" +
                "KeyIds=" + KeyIds +
                '}';
    }
}
