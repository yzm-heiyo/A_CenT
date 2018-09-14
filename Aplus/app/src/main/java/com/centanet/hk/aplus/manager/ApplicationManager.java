package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.bean.complexSearch.Operation;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.params.Parameter;
import com.centanet.hk.aplus.bean.params.SystemParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理A+所有的静态变量
 * Created by yangzm4 on 2018/3/20.
 */

public class ApplicationManager {

    private static Map<String, String> statusCode;
    private static List<DistrictItem> districtItems;

    public static MyApplication getApplication() {
        return (MyApplication) MyApplication.getContext().getApplicationContext();
    }

    public static void setOpenDataType(int type) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setOpenDataType(type);
    }

    public static int getOpenDataType() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getOpenDataType();
    }

    public static void setParamter(Parameter paramter) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setParameter(paramter);
    }

    public static void setLabelSystemParam(SystemParam labelSystemParam) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setLabelSystenParam(labelSystemParam);
    }

    public static void setHouseOperation(Operation operation) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setHouseOperation(operation);
    }

    public static void setFavoOperation(Operation operation) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setFavoOperation(operation);
    }

    public static void setDistrictItems(List<DistrictItem> districtItems) {
        ApplicationManager.districtItems = districtItems;
    }

    public static List<DistrictItem> getDistrictItems() {
        return districtItems;
    }

    public static Operation getFavoOperation() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getFavoOperation();
    }

    public static Parameter getParamter() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getParameter();
    }

    public static Operation getHouseOperation() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getHouseOperation();
    }

    public static SystemParam getLabelSystenParam() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getLabelSystenParam();
    }


    public static SystemParam getDirectionSystemParam() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getDirectionSystemParam();
    }

    public static SystemParam getIntervalSystemParam() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getIntervalSystemParam();
    }

    public static List<String> getContactType() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getContactType();
    }

    public static void setContactType(List<String> contactType) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setContactType(contactType);
    }

    public static void setDirectionSystemParam(SystemParam directionSystemParam) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setDirectionSystemParam(directionSystemParam);
    }

    public static void setIntervalSystemParam(SystemParam intervalSystemParam) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setIntervalSystemParam(intervalSystemParam);
    }


    public static void setStatusParams(Map<String, String> params) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setStatusParams(params);
    }

    public static void setStatusCode(Map<String, String> params) {
        ((MyApplication) MyApplication.getContext().getApplicationContext()).setStatusCodes(params);
    }

    public static Map<String, String> getStatusParams() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getStatusParams();
    }

    public static Map<String, String> getStatusCode() {
        return ((MyApplication) MyApplication.getContext().getApplicationContext()).getStatusCodes();
    }

    public static List<String> getStatusText() {
        Map<String, String> statusParams = ApplicationManager.getStatusParams();
        List<String> statuList = new ArrayList<>();
        for (Map.Entry<String, String> entry : statusParams.entrySet()) {
            statuList.add(entry.getKey());
        }
        return statuList;
    }

    public static List<String> getStatusValue(String[] status) {
        if (status == null) return null;
        Map<String, String> statusParams = ApplicationManager.getStatusParams();
        List<String> statuList = new ArrayList<>();
        for (String sta : status) {
            for (Map.Entry<String, String> entry : statusParams.entrySet()) {
                if (entry.getKey().equals(sta.substring(0, 1))) {
                    statuList.add(entry.getValue());
                }
            }
        }
        return statuList;
    }

    public static String getSelectStatusText(String[] status) {
        String statusStr = "";
        if (status != null && status.length != 0) {
            for (String str : status) {
                if (str.equals("全部")) return "全部";
                statusStr = statusStr + str;
            }
            return statusStr;
        }
        return "全部";
    }
}
