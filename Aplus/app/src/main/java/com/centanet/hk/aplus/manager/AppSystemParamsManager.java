package com.centanet.hk.aplus.manager;

import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.bean.build_tag.TagCategory;
import com.centanet.hk.aplus.bean.params.Parameter;
import com.centanet.hk.aplus.bean.params.SystemParam;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.util.List;

/**
 * 管理App所有系统参数
 * 可以通过参数枚举(APSystemParameterType.class)获取对应参数
 * Created by yangzm4 on 2018/6/28.
 */

public class AppSystemParamsManager {

    public static Parameter systemParams;
    public static List<TagCategory> TagCategorys;//设施

    public static SystemParam getSystemParams(int paramType) {

        SystemParam systemParam = null;

        for (SystemParam p : systemParams.getSystemParam()) {
            L.d("p", p.toString());
            if (paramType == p.getParameterType())
                systemParam = p;
        }

        return systemParam;
    }

    public static void setTagCategorys(List<TagCategory> tagCategorys) {
        TagCategorys = tagCategorys;
    }

    public static List<TagCategory> getTagCategorys() {
        return TagCategorys;
    }

    public static boolean isNeedToUpdate() {
        return systemParams.isNeedUpdate();
    }

    public static void setSystemParams(String systemParams) {
        try {
            AppSystemParamsManager.systemParams = GsonUtil.parseJson(systemParams, Parameter.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    //todo 有待改进 速度应该可以再提升
    public static String[] getValue(int paramType, List<String> list) {
        SystemParam systemParam = getSystemParams(paramType);
        String[] values = new String[list.size()];

        for (int i = 0; i < list.size(); i++)
            values[i] = getValue(systemParam, list.get(i));

        return values;
    }


    /**
     * 获得code对应得value //code 200 value N
     * @param systemParam
     * @param key
     * @return
     */
    public static String getValue(SystemParam systemParam, String key) {
        List<SystemParamItems> items = systemParam.getSystemParamItems();
        for (SystemParamItems s : items) {
            if (s.getItemCode().equals(key))
                return s.getItemText();
        }
        return null;
    }
}
