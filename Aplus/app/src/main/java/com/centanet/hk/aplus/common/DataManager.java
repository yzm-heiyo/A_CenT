package com.centanet.hk.aplus.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.common.CommandField.PriceType.SALE;

/**
 * Created by yangzm4 on 2018/1/19.
 */

public class DataManager {
    public static List<Integer> checkBoxSelecterList = new ArrayList<>();
    //標識數據是否到尾
    public static boolean isNetWorkDataEnd = false;
    //列表數據
    public static List<Map<String, String>> parameter;
    //價格選中區間
    public static int priceDialogSeletedId;
    //價格區間價格
    public static String[] priceInterval = new String[2];
    //價格顯示類型
    public static int priceType = SALE;
    //排序id
    public static int sortDialogSelectId;
}