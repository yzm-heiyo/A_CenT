package com.centanet.hk.aplus.bean.userdesign_option;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.save_option.TransRequestSaveParams;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;

import org.litepal.crud.LitePalSupport;

/**
 * Created by yangzm4 on 2018/8/6.
 */

public class TransSearchHistory extends LitePalSupport {

    //    private long id;
    private String optionMame;
    private TransListRequest request;
    private TransRequestSaveParams params;

    public TransSearchHistory() {
//        houseDescription.setPriceUnitTo("1922200");
    }

    public TransSearchHistory(TransListRequest houseDescription, TransRequestSaveParams manager) {
        this.request = houseDescription;
        this.params = manager;
    }

    public void setHouseDescription(TransListRequest houseDescription) {
        this.request = houseDescription;
    }

    public TransSearchHistory(PropertyRequestParamsManager manager) {
//        this.manager = new PropertyRequestParamsManager();
        L.d("getUseDesign_ProManager", manager.toString());
    }

//    public void setManager(PropertyRequestParamsManager manager) {
//        this.manager = manager;
//    }

    public void setOptionMame(String optionMame) {
        this.optionMame = optionMame;
    }

    public String getOptionMame() {
        return optionMame;
    }

    public TransListRequest getHouseDescription() {
        return request;
    }

    public TransRequestSaveParams getManager() {
        return params;
    }

    @Override
    public String toString() {
        return "PropertySearchHistory{" +
                "houseDescription=" + request +
                ", manager=" + params +
                '}';
    }
}
