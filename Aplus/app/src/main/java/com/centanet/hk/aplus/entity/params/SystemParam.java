package com.centanet.hk.aplus.entity.params;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/2/2.
 */

public class SystemParam implements Serializable{

    String ParameterName;
    int ParameterType;
    String ParameterStatus;
    List<SystemParamItems> Items;

    public SystemParam() {
    }

    public SystemParam(String parameterName, int parameterType, String parameterStatus, List<com.centanet.hk.aplus.entity.params.SystemParamItems> systemParamItems, String flag, String errorMsg, String runTime) {
        ParameterName = parameterName;
        ParameterType = parameterType;
        ParameterStatus = parameterStatus;
        Items = systemParamItems;
    }

    public void setParameterName(String parameterName) {
        ParameterName = parameterName;
    }

    public void setParameterType(int parameterType) {
        ParameterType = parameterType;
    }

    public void setParameterStatus(String parameterStatus) {
        ParameterStatus = parameterStatus;
    }

    public void setSystemParamItems(List<com.centanet.hk.aplus.entity.params.SystemParamItems> systemParamItems) {
        Items = systemParamItems;
    }

    public String getParameterName() {
        return ParameterName;
    }

    public int getParameterType() {
        return ParameterType;
    }

    public String getParameterStatus() {
        return ParameterStatus;
    }

    public List<com.centanet.hk.aplus.entity.params.SystemParamItems> getSystemParamItems() {
        return Items;
    }


    @Override
    public String toString() {
        return "SystemParam{" +
                "ParameterName='" + ParameterName + '\'' +
                ", ParameterType='" + ParameterType + '\'' +
                ", ParameterStatus='" + ParameterStatus + '\'' +
                ", SystemParamItems=" + Items +
                '}';
    }
}
