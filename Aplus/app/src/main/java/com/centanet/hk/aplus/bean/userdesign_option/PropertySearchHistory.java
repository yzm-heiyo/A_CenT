package com.centanet.hk.aplus.bean.userdesign_option;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;

import org.litepal.crud.LitePalSupport;

/**
 * Created by yangzm4 on 2018/8/6.
 */

public class PropertySearchHistory extends LitePalSupport {

    //    private long id;
    private String optionMame;
    private HouseDescription houseDescription;
    private PropertyRequestSaveParams manager;

    public PropertySearchHistory() {
//        houseDescription.setPriceUnitTo("1922200");
    }

    public PropertySearchHistory(HouseDescription houseDescription, PropertyRequestSaveParams manager) {
        this.houseDescription = houseDescription;
        this.manager = manager;
    }

    public void setHouseDescription(HouseDescription houseDescription) {
        this.houseDescription = houseDescription;
    }

    public PropertySearchHistory(PropertyRequestParamsManager manager) {
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

    public HouseDescription getHouseDescription() {
        return houseDescription;
    }

    public PropertyRequestSaveParams getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "PropertySearchHistory{" +
                "houseDescription=" + houseDescription +
                ", manager=" + manager +
                '}';
    }
}
